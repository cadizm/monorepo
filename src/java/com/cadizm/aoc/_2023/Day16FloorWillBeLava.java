package com.cadizm.aoc._2023;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.google.common.collect.ImmutableMap;

// https://adventofcode.com/2023/day/16
public class Day16FloorWillBeLava {

  enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
  }

  enum TileType {
    EMPTY_SPACE("."),
    FORWARD_MIRROR("/"),
    BACKWARD_MIRROR("\\"),
    VERTICAL_SPLITTER("|"),
    HORIZONTAL_SPLITTER("-");

    final String type;

    TileType(String type) {
      this.type = type;
    }

    public static TileType findByType(String type) {
      return switch (type) {
        case "." -> EMPTY_SPACE;
        case "/" -> FORWARD_MIRROR;
        case "\\" -> BACKWARD_MIRROR;
        case "|" -> VERTICAL_SPLITTER;
        case "-" -> HORIZONTAL_SPLITTER;
        default -> throw new RuntimeException();
      };
    }
  }

  record Reflection(Direction direction, TileType type) {}

  record PositionVector(int row, int col, Direction direction) {}

  record Position(int row, int col) {}

  record Tile(int row, int col, TileType type) {
    @Override
    public String toString() {
      return type.type;
    }
  }

  private final Tile[][] grid;

  public Day16FloorWillBeLava(String inputPath) {
    this.grid = readInput(inputPath);
  }

  public long puzzle1() {
    return getNumberOfEnergizedTiles(new PositionVector(0, 0, Direction.RIGHT));
  }

  public long puzzle2() {
    long maxEnergizedTiles = 0;

    int row;
    int col;

    // Top row, going down
    row = 0;
    for (col = 0; col < grid[row].length; ++col) {
      PositionVector pv = new PositionVector(row, col, Direction.DOWN);
      maxEnergizedTiles = Math.max(maxEnergizedTiles, getNumberOfEnergizedTiles(pv));
    }

    // Bottom row, going up
    row = grid.length - 1;
    for (col = 0; col < grid[row].length; ++col) {
      PositionVector pv = new PositionVector(row, col, Direction.UP);
      maxEnergizedTiles = Math.max(maxEnergizedTiles, getNumberOfEnergizedTiles(pv));
    }

    // Leftmost col, going right
    col = 0;
    for (row = 0; row < grid.length; ++row) {
      PositionVector pv = new PositionVector(row, col, Direction.RIGHT);
      maxEnergizedTiles = Math.max(maxEnergizedTiles, getNumberOfEnergizedTiles(pv));
    }

    // Rightmost col, going left
    col = grid[0].length;
    for (row = 0; row < grid.length; ++row) {
      PositionVector pv = new PositionVector(row, col, Direction.LEFT);
      maxEnergizedTiles = Math.max(maxEnergizedTiles, getNumberOfEnergizedTiles(pv));
    }

    return maxEnergizedTiles;
  }

  int getNumberOfEnergizedTiles(PositionVector positionVector) {
    Set<PositionVector> seen = new HashSet<>();

    Deque<PositionVector> queue = new ArrayDeque<>();
    queue.add(positionVector);

    while (!queue.isEmpty()) {
      PositionVector pv = queue.remove();

      if (outOfBounds(pv.row, pv.col) ||
          (!isEmptySpace(pv.row, pv.col) && seen.contains(pv))) {
        continue;
      }

      seen.add(pv);

      Tile tile = grid[pv.row][pv.col];
      TileType type = tile.type;

      List<Direction> nextDirections = getNextDirection(pv.direction, type);
      List<PositionVector> nextPositionVectors = nextDirections.stream()
          .map(direction -> getNextPositionVector(new PositionVector(pv.row, pv.col, direction)))
          .toList();

      for (var nextVector : nextPositionVectors) {
        queue.addFirst(nextVector);
      }
    }

    return seen.stream()
        .map(pos -> new Position(pos.row, pos.col))
        .collect(Collectors.toSet())
        .size();
  }

  boolean isEmptySpace(int row, int col) {
    return grid[row][col].type == TileType.EMPTY_SPACE;
  }

  boolean outOfBounds(int row, int col) {
    return row < 0 || row >= grid.length ||
        col < 0 || col >= grid[row].length;
  }

  static PositionVector getNextPositionVector(PositionVector positionVector) {
    int row = positionVector.row;
    int col = positionVector.col;
    Direction direction = positionVector.direction;

    return switch (direction) {
      case UP -> new PositionVector(row - 1, col, direction);
      case DOWN -> new PositionVector(row + 1, col, direction);
      case LEFT -> new PositionVector(row, col - 1, direction);
      case RIGHT -> new PositionVector(row, col + 1, direction);
    };
  }

  static List<Direction> getNextDirection(Direction direction, TileType type) {
    Map<Reflection, List<Direction>> map = new ImmutableMap.Builder<Reflection, List<Direction>>()
        .put(new Reflection(Direction.UP, TileType.EMPTY_SPACE), List.of(Direction.UP))
        .put(new Reflection(Direction.DOWN, TileType.EMPTY_SPACE), List.of(Direction.DOWN))
        .put(new Reflection(Direction.LEFT, TileType.EMPTY_SPACE), List.of(Direction.LEFT))
        .put(new Reflection(Direction.RIGHT, TileType.EMPTY_SPACE), List.of(Direction.RIGHT))
        .put(new Reflection(Direction.UP, TileType.FORWARD_MIRROR), List.of(Direction.RIGHT))
        .put(new Reflection(Direction.DOWN, TileType.FORWARD_MIRROR), List.of(Direction.LEFT))
        .put(new Reflection(Direction.LEFT, TileType.FORWARD_MIRROR), List.of(Direction.DOWN))
        .put(new Reflection(Direction.RIGHT, TileType.FORWARD_MIRROR), List.of(Direction.UP))
        .put(new Reflection(Direction.UP, TileType.BACKWARD_MIRROR), List.of(Direction.LEFT))
        .put(new Reflection(Direction.DOWN, TileType.BACKWARD_MIRROR), List.of(Direction.RIGHT))
        .put(new Reflection(Direction.LEFT, TileType.BACKWARD_MIRROR), List.of(Direction.UP))
        .put(new Reflection(Direction.RIGHT, TileType.BACKWARD_MIRROR), List.of(Direction.DOWN))
        .put(new Reflection(Direction.UP, TileType.VERTICAL_SPLITTER), List.of(Direction.UP))
        .put(new Reflection(Direction.DOWN, TileType.VERTICAL_SPLITTER), List.of(Direction.DOWN))
        .put(new Reflection(Direction.LEFT, TileType.VERTICAL_SPLITTER), List.of(Direction.UP, Direction.DOWN))
        .put(new Reflection(Direction.RIGHT, TileType.VERTICAL_SPLITTER), List.of(Direction.UP, Direction.DOWN))
        .put(new Reflection(Direction.UP, TileType.HORIZONTAL_SPLITTER), List.of(Direction.LEFT, Direction.RIGHT))
        .put(new Reflection(Direction.DOWN, TileType.HORIZONTAL_SPLITTER), List.of(Direction.LEFT, Direction.RIGHT))
        .put(new Reflection(Direction.LEFT, TileType.HORIZONTAL_SPLITTER), List.of(Direction.LEFT))
        .put(new Reflection(Direction.RIGHT, TileType.HORIZONTAL_SPLITTER), List.of(Direction.RIGHT))
        .build();

    return map.get(new Reflection(direction, type));
  }

  Tile[][] readInput(String path) {
    Stream<String> stream = Resource.readLines(path);

    List<String[]> lines = new ArrayList<>();

    for (var iter = stream.iterator(); iter.hasNext(); ) {
      lines.add(iter.next().split(""));
    }

    int rows = lines.size();
    int cols = lines.get(0).length;

    Tile[][] grid = new Tile[rows][cols];

    for (int row = 0; row < rows; ++row) {
      for (int col = 0; col < cols; ++col) {
        TileType type = TileType.findByType(lines.get(row)[col]);
        grid[row][col] = new Tile(row, col, type);
      }
    }

    return grid;
  }
}
