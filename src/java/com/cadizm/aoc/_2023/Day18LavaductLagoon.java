package com.cadizm.aoc._2023;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cadizm.io.Resource;

// https://adventofcode.com/2023/day/18
public class Day18LavaductLagoon {

  record Instruction(Direction direction, int magnitude, String colorCode) {}

  enum Direction {
    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    private final int rowOffset;
    private final int colOffset;

    Direction(int rowOffset, int colOffset) {
      this.rowOffset = rowOffset;
      this.colOffset = colOffset;
    }
  }

  record Point(int row, int col) {
    @Override
    public String toString() {
      return String.format("(%s,%s)", row, col);
    }

    public Point move(Direction direction, int magnitude) {
      return new Point(
          row + (magnitude * direction.rowOffset),
          col + (magnitude * direction.colOffset));
    }
  }

  record Edge(Point start, Point end, Direction direction) {}

  private final List<Instruction> instructions;

  public Day18LavaductLagoon(String inputPath) {
    this.instructions = readInput(inputPath);
  }

  public long puzzle1() {
    List<Edge> edges = new ArrayList<>();

    Point start = new Point(0, 0);
    for (var instruction : instructions) {
      Point end = start.move(instruction.direction, instruction.magnitude);
      edges.add(new Edge(start, end, instruction.direction));
      start = end;
    }

    String[][] grid = buildGrid(recenter(edges));

    int count = 0;
    for (int row = 0; row < grid.length; ++row) {
      for (int col = 0; col < grid[row].length; ++col) {
        if (grid[row][col].equals("#") || grid[row][col].equals(".")) {
          count += 1;
        }
      }
    }

    return count;
  }

  public long puzzle2() {
    List<Instruction> translated = translate(instructions);

    List<Point> boundaryPoints = new ArrayList<>();

    // Using the list of instructions, giving us the start/end points of the
    // edges in the polygon, "fill in" the points between start and end and
    // add to our list of polygon boundary points.
    Point point = new Point(0, 0);
    for (var instruction : translated) {
      Point end = point.move(instruction.direction, instruction.magnitude);

      while (!point.equals(end)) {
        boundaryPoints.add(point);
        point = point.move(instruction.direction, 1);
      }
    }

    // Use the Shoelace formula to calculate the polygon's area using its boundary
    // points. Note that the points must be "oriented" correctly such that they
    // are ordered positively (counter-clockwise) or negatively (clockwise) so
    // that the boundary can be drawn by following the points in the given order.
    long area = 0;
    for (int i = 0; i < boundaryPoints.size(); ++i) {
      Point a = boundaryPoints.get(i);
      Point b = boundaryPoints.get((i + 1) % boundaryPoints.size());

      area += (long)(a.row + b.row) * (a.col - b.col);
    }
    area = Math.abs(area / 2);

    // Use Pick's theorem to calculate number of interior points:
    //   Area = interior + (boundary / 2) - 1
    //   interior = Area - (boundary / 2) + 1
    long interiorPointsSize = area - (boundaryPoints.size() / 2) + 1;

    return boundaryPoints.size() + interiorPointsSize;
  }

  List<Instruction> translate(List<Instruction> instructions) {
    return instructions.stream()
        .map(this::translate)
        .toList();
  }

  // Each hexadecimal code is six hexadecimal digits long.
  // The first five hexadecimal digits encode the distance
  // The last hexadecimal digit encodes the direction to dig:
  //   0 means R
  //   1 means D
  //   2 means L
  //   3 means U
  Instruction translate(Instruction instruction) {
    int magnitude = Integer.parseInt(instruction.colorCode.substring(0, 5), 16);
    Direction direction = switch (instruction.colorCode.charAt(5)) {
      case '0' -> Direction.RIGHT;
      case '1' -> Direction.DOWN;
      case '2' -> Direction.LEFT;
      case '3' -> Direction.UP;
      default -> throw new RuntimeException();
    };

    return new Instruction(direction, magnitude, instruction.colorCode);
  }

  // Recenter to account for any movements left or above origin
  List<Edge> recenter(List<Edge> edges) {
    // Get unique set of points from list of edges
    Set<Point> points = edges.stream()
        .map(edge -> List.of(edge.start, edge.end))
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());

    if (!points.contains(new Point(0, 0))) {
      throw new RuntimeException("Point (0,0) not found");
    }

    int minRow = points.stream()
        .map(Point::row)
        .min(Integer::compare)
        .orElseThrow();

    int minCol = points.stream()
        .map(Point::col)
        .min(Integer::compare)
        .orElseThrow();

    // Increment offset by 1 to shift points "down and right"
    // (i.e. add top and left borders)
    final int rowOffset = Math.abs(minRow) + 1;
    final int colOffset = Math.abs(minCol) + 1;

    return edges.stream()
        .map(edge -> recenter(edge, rowOffset, colOffset))
        .toList();
  }

  Edge recenter(Edge edge, int rowOffset, int colOffset) {
    return new Edge(
        recenter(edge.start, rowOffset, colOffset),
        recenter(edge.end, rowOffset, colOffset),
        edge.direction);
  }

  Point recenter(Point point, int rowOffset, int colOffset) {
    return new Point(point.row + rowOffset, point.col + colOffset);
  }

  String[][] buildGrid(List<Edge> edges) {
    // Get unique set of points from list of edges
    Set<Point> points = edges.stream()
        .map(edge -> List.of(edge.start, edge.end))
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());

    // Calculate grid size
    // Increment by 1 here to account for 0-based indexing
    int rows = 1 + points.stream()
        .map(Point::row)
        .max(Integer::compare)
        .orElseThrow();

    // Increment by 1 here to account for 0-based indexing
    int cols = 1 + points.stream()
        .map(Point::col)
        .max(Integer::compare)
        .orElseThrow();

    // Initialize grid with empty space
    // Increment rows and cols by 1 to add bottom and right borders
    String[][] buf = new String[rows + 1][cols + 1];
    for (int i = 0; i < buf.length; ++i) {
      Arrays.fill(buf[i], ".");
    }

    // Fill in points between edge start and end
    for (var edge : edges) {
      Point pos = edge.start;
      while (!pos.equals(edge.end)) {
        points.add(pos);
        buf[pos.row][pos.col] = "#";
        pos = pos.move(edge.direction, 1);
      }
    }

    // Flood-fill exterior points with "spaces"
    Deque<Point> queue = new ArrayDeque<>();
    queue.add(new Point(0, 0));

    Set<Point> seen = new HashSet<>();

    while (!queue.isEmpty()) {
      Point point = queue.remove();

      if (seen.contains(point) || !inBounds(point, buf) ||
          buf[point.row][point.col].equals("#")) {
        continue;
      }

      seen.add(point);
      buf[point.row][point.col] = " ";

      for (var direction : List.of(Direction.values())) {
        queue.add(point.move(direction, 1));
      }
    }

    return buf;
  }

  boolean inBounds(Point point, Object[][] grid) {
    return inBounds(point, grid.length, grid[0].length);
  }

  boolean inBounds(Point point, int rows, int cols) {
    return point.row >= 0 && point.row < rows &&
        point.col >= 0 && point.col < cols;
  }

  List<Instruction> readInput(String path) {
    Stream<String> stream = Resource.readLines(path);

    List<Instruction> instructions = new ArrayList<>();

    for (var iter = stream.iterator(); iter.hasNext(); ) {
      String[] parts = iter.next().split(" ");
      Direction direction = switch (parts[0]) {
        case "U" -> Direction.UP;
        case "R" -> Direction.RIGHT;
        case "D" -> Direction.DOWN;
        case "L" -> Direction.LEFT;
        default -> throw new RuntimeException();
      };
      int magnitude = Integer.parseInt(parts[1]);
      String colorCode = parts[2].replaceAll("[#\\(\\)]", "");

      instructions.add(new Instruction(direction, magnitude, colorCode));
    }

    return instructions;
  }
}
