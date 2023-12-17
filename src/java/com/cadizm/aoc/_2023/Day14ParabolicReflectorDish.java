package com.cadizm.aoc._2023;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

import com.cadizm.io.Resource;

// https://adventofcode.com/2023/day/14
public class Day14ParabolicReflectorDish {

  enum TileType {
    EMPTY("."),
    ROUND("O"),
    CUBE("#");

    final String value;

    TileType(String value) {
      this.value = value;
    }

    String value() {
      return value;
    }

    static TileType findByValue(String value) {
      return switch (value) {
        case "." -> TileType.EMPTY;
        case "O" -> TileType.ROUND;
        case "#" -> TileType.CUBE;
        default -> throw new RuntimeException();
      };
    }
  }

  record Tile(TileType type) {
    @Override
    public String toString() {
      return String.format("%s", type.value());
    }
  }

  private final Tile[][] grid;

  public Day14ParabolicReflectorDish(String inputPath) {
    this.grid = readInput(inputPath);
  }

  public long puzzle1() {
    long sum = 0;

    // Map from Column -> Map<Row, Tile>
    TreeMap<Integer, TreeMap<Integer, Tile>> map = new TreeMap<>();

    // Initialize map with non-empty tiles
    for (int row = 0; row < grid.length; ++row) {
      for (int col = 0; col < grid[row].length; ++col) {
        Tile tile = grid[row][col];
        map.computeIfAbsent(col, k -> new TreeMap<>());

        if (tile.type != TileType.EMPTY) {
          map.get(col).put(row, tile);
        }
      }
    }

    // Scan grid by column from top-to-bottom
    // If the current position is empty, find the next higher entry
    // If a higher entry exists, move it to the current position
    for (int row = 0; row < grid.length; ++row) {
      for (int col = 0; col < grid[row].length; ++col) {
        if (map.get(col).get(row) == null) {
          var entry = map.get(col).higherEntry(row);

          if (entry != null) {
            int from = entry.getKey();
            Tile move = entry.getValue();

            if (move.type == TileType.ROUND && from != row) {
              map.get(col).remove(from);
              map.get(col).put(row, move);
            }
          }
        }
      }
    }

    // Go through all the tiles and calculate the sum according to its row
    for (int row = 0; row < grid.length; ++row) {
      for (int col = 0; col < grid[row].length; ++col) {
        Tile tile = map.get(col).get(row);

        if (tile != null && tile.type == TileType.ROUND) {
          sum += grid.length - row;
        }
      }
    }

    return sum;
  }

  public long puzzle2() {
    long sum = 0;

    return sum;
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
        String val = lines.get(row)[col];
        grid[row][col] = new Tile(TileType.findByValue(val));
      }
    }

    return grid;
  }
}
