package com.cadizm.aoc._2023;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.google.common.base.Preconditions;

// https://adventofcode.com/2023/day/13
public class Day13PointOfIncidence {

  enum TileType {
    ASH,
    ROCK
  }

  record Tile(int row, int col, TileType type) {
    @Override
    public String toString() {
      if (type == TileType.ASH) {
        return ".";
      }

      return String.format("(%s, %s)", row, col);
    }
  }

  private final List<Tile[][]> patterns;

  public Day13PointOfIncidence(String inputPath) {
    this.patterns = readInput(inputPath);
  }

  public long puzzle1() {
    long sum = 0;

    for (var pattern : patterns) {
      int col = verticalReflection(pattern);
      int row = horizontalReflection(pattern);

      Preconditions.checkArgument(!(col == -1 && row == -1));

      if (col != -1) {
        sum += col;
      }

      if (row != -1) {
        sum += 100L * row;
      }
    }

    return sum;
  }

  public long puzzle2(long factor) {
    return 0;
  }

  /**
   * Return the right position in a vertical reflection or -1 if one doesn't exist.
   */
  int verticalReflection(Tile[][] pattern) {
    int cols = pattern[0].length;

    for (int col = 0; col < cols - 1; ++col) {
      if (columnsReflect(col, col + 1, pattern)) {
        // found innermost reflection, check to see it extends to boundaries
        int left = col;
        int right = left + 1;

        boolean flag = true;
        while (left >= 0 && right < cols) {
          if (!columnsReflect(left, right, pattern)) {
            flag = false;
            break;
          }

          left -= 1;
          right += 1;
        }

        if (flag) {
          return col + 1;
        }
      }
    }

    return -1;
  }

  /**
   * Return the bottom position in a horizontal reflection or -1 if one doesn't exist.
   */
  int horizontalReflection(Tile[][] pattern) {
    int rows = pattern.length;

    for (int row = 0; row < rows - 1; ++row) {
      if (rowsReflect(row, row + 1, pattern)) {
        // found innermost reflection, check to see it extends to boundaries
        int top = row;
        int bottom = top + 1;

        boolean flag = true;
        while (top >= 0 && bottom < rows) {
          if (!rowsReflect(top, bottom, pattern)) {
            flag = false;
            break;
          }

          top -= 1;
          bottom += 1;
        }

        if (flag) {
          return row + 1;
        }
      }
    }

    return -1;
  }

  // Note: no bounds checking
  boolean columnsReflect(int i, int j, Tile[][] pattern) {
    Preconditions.checkArgument(i != j);

    for (int row = 0; row < pattern.length; ++row) {
      Tile a = pattern[row][i];
      Tile b = pattern[row][j];

      if (a.type != b.type) {
        return false;
      }
    }

    return true;
  }

  // Note: no bounds checking
  boolean rowsReflect(int i, int j, Tile[][] pattern) {
    Preconditions.checkArgument(i != j);

    for (int col = 0; col < pattern[0].length; ++col) {
      Tile a = pattern[i][col];
      Tile b = pattern[j][col];

      if (a.type != b.type) {
        return false;
      }
    }

    return true;
  }

  List<Tile[][]> readInput(String path) {
    Stream<String> stream = Resource.readLines(path);

    List<Tile[][]> patterns = new ArrayList<>();

    List<String> lines = new ArrayList<>();
    for (var iter = stream.iterator(); iter.hasNext(); ) {
      String line = iter.next();

      if (line.isBlank()) {
        patterns.add(buildGrid(lines));
        lines = new ArrayList<>();
      } else {
        lines.add(line);
      }
    }

    patterns.add(buildGrid(lines));

    return patterns;
  }

  Tile[][] buildGrid(List<String> lines) {
    int rows = lines.size();
    int cols = lines.get(0).length();

    Tile[][] grid = new Tile[rows][cols];

    for (int row = 0; row < rows; ++row) {
      for (int col = 0; col < cols; ++col) {
        char val = lines.get(row).charAt(col);
        TileType type = val == '#' ? TileType.ROCK : TileType.ASH;
        grid[row][col] = new Tile(row, col, type);
      }
    }

    return grid;
  }
}
