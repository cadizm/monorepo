package com.cadizm.aoc._2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Stream;

import com.cadizm.io.Resource;

// https://adventofcode.com/2023/day/17
public class Day17ClumsyCrucible {

  enum Part {
    PART1,
    PART2
  }

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

    public Direction left() {
      return switch (this) {
        case UP -> LEFT;
        case RIGHT -> UP;
        case DOWN -> RIGHT;
        case LEFT -> DOWN;
      };
    }

    public Direction right() {
      return switch (this) {
        case UP -> RIGHT;
        case RIGHT -> DOWN;
        case DOWN -> LEFT;
        case LEFT -> UP;
      };
    }
  }

  record Position(int row, int col) {
    @Override
    public String toString() {
      return String.format("(%s,%s)", row, col);
    }

    public Position move(Direction direction) {
      return new Position(row + direction.rowOffset, col + direction.colOffset);
    }
  }

  record Tile(Position position, int weight) {
    @Override
    public String toString() {
      return String.format("position=%s weight=%s", position, weight);
    }
  }

  record TileVector(Tile tile, Direction direction, int steps) {}

  record TileState(TileVector tileVector, int cost) implements Comparable<TileState> {
    @Override
    public int compareTo(TileState other) {
      return this.cost - other.cost;
    }
  }

  private final Tile[][] grid;

  // Use modified Dijkstra's algorithm. There are 2 key insights needed here:
  //   1. Model the state as: current position, current direction, and
  //      number of steps in current direction
  //   2. Using the current tile state, determine the valid next directions
  //      that can be moved and reset step count as necessary when turning
  //      90 degrees (left or right)
  public Day17ClumsyCrucible(String inputPath) {
    this.grid = readInput(inputPath);
  }

  public long puzzle1() {
    int rows = grid.length;
    int cols = grid[0].length;

    Tile start = grid[0][0];
    Tile end = grid[rows - 1][cols - 1];

    return search(start, end, Part.PART1);
  }

  public long puzzle2() {
    int rows = grid.length;
    int cols = grid[0].length;

    Tile start = grid[0][0];
    Tile end = grid[rows - 1][cols - 1];

    return search(start, end, Part.PART2);
  }

  public int search(Tile start, Tile end, Part part) {
    PriorityQueue<TileState> queue = new PriorityQueue<>();

    queue.add(new TileState(new TileVector(start, Direction.RIGHT, 1), 0));
    queue.add(new TileState(new TileVector(start, Direction.DOWN, 1), 0));

    Set<TileVector> seen = new HashSet<>();

    while (!queue.isEmpty()) {
      TileState currentState = queue.remove();

      TileVector currentTileVector = currentState.tileVector;
      Tile currentTile = currentTileVector.tile;

      Position currentPosition = currentTile.position;
      Direction currentDirection = currentTileVector.direction;

      int currentCost = currentState.cost;
      int currentSteps = currentTileVector.steps;

      if (currentTile.equals(end)) {
        return currentCost;
      }

      if (seen.contains(currentTileVector)) {
        continue;
      }

      seen.add((currentTileVector));

      List<Direction> nextDirections = switch (part) {
        case PART1 -> getPart1NextDirections(currentDirection, currentSteps);
        case PART2 -> getPart2NextDirections(currentDirection, currentSteps);
      };

      for (Direction nextDirection : nextDirections) {
        Position nextPosition = currentPosition.move(nextDirection);

        if (inBounds(nextPosition)) {
          Tile nextTile = grid[nextPosition.row][nextPosition.col];
          int nextSteps = nextDirection == currentDirection ? currentSteps + 1 : 1;
          TileVector nextTileVector = new TileVector(nextTile, nextDirection, nextSteps);
          TileState nextState = new TileState(nextTileVector, currentCost + nextTile.weight);
          queue.add(nextState);
        }
      }
    }

    throw new RuntimeException();
  }

  // Because it is difficult to keep the top-heavy crucible going in a straight line for
  // very long, it can move at most three blocks in a single direction before it must turn
  // 90 degrees left or right. The crucible also can't reverse direction; after entering
  // each city block, it may only turn left, continue straight, or turn right.
  List<Direction> getPart1NextDirections(Direction direction, int steps) {
    List<Direction> nextDirections = new ArrayList<>();

    if (steps < 3) {
      nextDirections.add(direction);
    }

    nextDirections.add(direction.left());
    nextDirections.add(direction.right());

    return nextDirections;
  }

  // Once an ultra crucible starts moving in a direction, it needs to move a minimum of
  // four blocks in that direction before it can turn (or even before it can stop at the
  // end). However, it will eventually start to get wobbly: an ultra crucible can move a
  // maximum of ten consecutive blocks without turning.
  List<Direction> getPart2NextDirections(Direction direction, int steps) {
    List<Direction> nextDirections = new ArrayList<>();

    if (steps >= 4) {
      nextDirections.add(direction.left());
      nextDirections.add(direction.right());
    }

    if (steps < 10) {
      nextDirections.add(direction);
    }

    return nextDirections;
  }

  boolean inBounds(Position position) {
    return inBounds(position.row, position.col);
  }

  boolean inBounds(int row, int col) {
    return row >= 0 && row < grid.length &&
        col >= 0 && col < grid[row].length;
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
        int weight = Integer.parseInt(lines.get(row)[col]);
        Tile tile = new Tile(new Position(row, col), weight);
        grid[row][col] = tile;
      }
    }

    return grid;
  }
}
