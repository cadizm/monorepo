package com.cadizm.aoc._2023;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.google.common.base.Preconditions;

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

  // Map from Row -> Map<Column, Tile>
  TreeMap<Integer, TreeMap<Integer, Tile>> rowMap = new TreeMap<>();

  // Map from Column -> Map<Row, Tile>
  TreeMap<Integer, TreeMap<Integer, Tile>> colMap = new TreeMap<>();

  public Day14ParabolicReflectorDish(String inputPath) {
    this.grid = readInput(inputPath);

    // Initialize column map with non-empty tiles
    for (int row = 0; row < grid.length; ++row) {
      for (int col = 0; col < grid[row].length; ++col) {
        Tile tile = grid[row][col];
        rowMap.computeIfAbsent(row, k -> new TreeMap<>());
        colMap.computeIfAbsent(col, k -> new TreeMap<>());

        if (tile.type != TileType.EMPTY) {
          rowMap.get(row).put(col, tile);
          colMap.get(col).put(row, tile);
        }
      }
    }
  }

  public long puzzle1() {
    long sum = 0;

    tiltNorth();

    // Go through all the tiles and calculate the sum according to its row
    for (int row = 0; row < grid.length; ++row) {
      for (int col = 0; col < grid[row].length; ++col) {

        Tile tile = colMap.get(col).get(row);
        if (tile != null && tile.type == TileType.ROUND) {
          sum += grid.length - row;
        }
      }
    }

    return sum;
  }

  public long puzzle2() {
    for (int i = 0; i < 1000; ++i) {
      cycle();
    }

    // Go through all the tiles and calculate the sum according to its row
    long sum = 0;
    for (int row = 0; row < grid.length; ++row) {
      for (int col = 0; col < grid[row].length; ++col) {

        Tile tile = colMap.get(col).get(row);
        if (tile != null && tile.type == TileType.ROUND) {
          sum += grid.length - row;
        }
      }
    }

    return sum;
  }

  void cycle() {
    tiltNorth();
    tiltWest();
    tiltSouth();
    tiltEast();
  }

  public void tiltNorth() {
    // Scan grid by column from top-to-bottom
    // If the current position is empty, find the next higher entry
    // If a higher entry exists, move it to the current position
    for (int row = 0; row < grid.length; ++row) {
      for (int col = 0; col < grid[row].length; ++col) {

        if (colMap.get(col).get(row) == null) {
          var entry = colMap.get(col).higherEntry(row);

          if (entry != null) {
            int from = entry.getKey();
            Tile move = entry.getValue();

            if (move.type == TileType.ROUND) {
              colMap.get(col).remove(from);
              colMap.get(col).put(row, move);

              rowMap.get(from).remove(col);
              rowMap.get(row).put(col, move);

              Preconditions.checkArgument(rowMap.get(row).get(col) == colMap.get(col).get(row));
            }
          }
        }
      }
    }
  }

  public void tiltSouth() {

    for (int row = grid.length - 1; row >= 0; --row) {
      for (int col = 0; col < grid[row].length; ++col) {

        if (colMap.get(col).get(row) == null) {
          var entry = colMap.get(col).lowerEntry(row);

          if (entry != null) {
            int from = entry.getKey();
            Tile move = entry.getValue();

            if (move.type == TileType.ROUND) {
              colMap.get(col).remove(from);
              colMap.get(col).put(row, move);

              rowMap.get(from).remove(col);
              rowMap.get(row).put(col, move);

              Preconditions.checkArgument(rowMap.get(row).get(col) == colMap.get(col).get(row));
            }
          }
        }
      }
    }
  }

  public void tiltEast() {
    for (int row = 0; row < grid.length; ++row) {
      for (int col = grid[row].length - 1; col >= 0; --col) {

        if (rowMap.get(row).get(col) == null) {
          var entry = rowMap.get(row).lowerEntry(col);

          if (entry != null) {
            int from = entry.getKey();
            Tile move = entry.getValue();

            if (move.type == TileType.ROUND) {
              rowMap.get(row).remove(from);
              rowMap.get(row).put(col, move);

              colMap.get(from).remove(row);
              colMap.get(col).put(row, move);

              Preconditions.checkArgument(rowMap.get(row).get(col) == colMap.get(col).get(row));
            }
          }
        }
      }
    }
  }

  public void tiltWest() {
    for (int row = 0; row < grid.length; ++row) {
      for (int col = 0; col < grid[row].length; ++col) {

        if (rowMap.get(row).get(col) == null) {
          var entry = rowMap.get(row).higherEntry(col);

          if (entry != null) {
            int from = entry.getKey();
            Tile move = entry.getValue();

            if (move.type == TileType.ROUND) {
              rowMap.get(row).remove(from);
              rowMap.get(row).put(col, move);

              colMap.get(from).remove(row);
              colMap.get(col).put(row, move);

              Preconditions.checkArgument(rowMap.get(row).get(col) == colMap.get(col).get(row));
            }
          }
        }
      }
    }
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
