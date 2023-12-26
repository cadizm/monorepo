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
      return type == TileType.ASH ? "." : "#";
    }
  }

  private final List<Tile[][]> patterns;

  public Day13PointOfIncidence(String inputPath) {
    this.patterns = readInput(inputPath);
  }

  public long puzzle1() {
    long sum = 0;

    for (var pattern : patterns) {
      int vertical = verticalReflection(pattern, List.of());
      int horizontal = horizontalReflection(pattern, List.of());

      Preconditions.checkArgument(!(vertical == -1 && horizontal == -1));

      if (vertical != -1) {
        sum += vertical;
      }

      if (horizontal != -1) {
        sum += 100L * horizontal;
      }
    }

    return sum;
  }

  public long puzzle2() {
    long sum = 0;

    patternLoop:
    for (var pattern : patterns) {
      int originalVertical = verticalReflection(pattern, List.of());
      int originalHorizontal = horizontalReflection(pattern, List.of());

      for (int row = 0; row < pattern.length; ++row) {
        for (int col = 0; col < pattern[row].length; ++col) {
          pattern[row][col] = flip(pattern[row][col]);

          int vertical = verticalReflection(pattern, List.of(originalVertical));
          int horizontal = horizontalReflection(pattern, List.of(originalHorizontal));

          if (!(vertical == -1 && horizontal == -1) &&
              (vertical != originalVertical || horizontal != originalHorizontal)) {

            if (vertical != -1) {
              sum += vertical;
            }

            if (horizontal != -1) {
              sum += 100L * horizontal;
            }

            continue patternLoop;
          }

          pattern[row][col] = flip(pattern[row][col]);
        }
      }
    }

    return sum;
  }

  Tile flip(Tile tile) {
    return new Tile(tile.row, tile.col, flip(tile.type));
  }

  TileType flip(TileType type) {
    return switch (type) {
      case ASH -> TileType.ROCK;
      case ROCK -> TileType.ASH;
    };
  }

  /**
   * Return the right position in a vertical reflection or -1 if one doesn't exist.
   */
  int verticalReflection(Tile[][] pattern, List<Integer> skipIndices) {
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

        if (flag && (!skipIndices.contains(col + 1))) {
          return col + 1;
        }
      }
    }

    return -1;
  }

  /**
   * Return the bottom position in a horizontal reflection or -1 if one doesn't exist.
   */
  int horizontalReflection(Tile[][] pattern, List<Integer> skipIndices) {
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

        if (flag && (!skipIndices.contains(row + 1))) {
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
