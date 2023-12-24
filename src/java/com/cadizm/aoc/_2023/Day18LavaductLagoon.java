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
    return 0;
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
    return point.row >= 0 && point.row < grid.length &&
        point.col >= 0 && point.col < grid[point.row].length;
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
      String colorCode = parts[2].replaceAll("[\\(\\)]", "");

      instructions.add(new Instruction(direction, magnitude, colorCode));
    }

    return instructions;
  }
}
