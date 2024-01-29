package com.cadizm.aoc._2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cadizm.io.Resource;

// https://adventofcode.com/2023/day/3
public class Day03GearRatios {

  // Create matrix and process row by row
  // Accumulate digits into number
  // Keeping track if a digit in accumulated number is adjacent to symbol
  // At end of row, if adjacent, add number to result sum
  public int puzzle1() {
    Character[][] matrix = readMatrix();

    int sum = 0;

    for (int row = 0; row < matrix.length; ++row) {
      for (int col = 0; col < matrix[row].length; ++col) {
        char c = matrix[row][col];

        if (Character.isDigit(c)) {
          boolean adjacentSymbol = false;
          int number = 0;

          while (Character.isDigit(c)) {
            number = number * 10 + Character.getNumericValue(c);
            adjacentSymbol = adjacentSymbol || hasAdjacentSymbol(row, col, matrix);
            if (col + 1 == matrix[row].length) {
              break;
            }
            c = matrix[row][++col];
          }

          if (adjacentSymbol) {
            sum += number;
          }
        }
      }
    }

    return sum;
  }

  record Coord(int row, int col) {}

  // Similar to part 1, process grid and parse numbers on grid.
  // In addition, maintain 2 new things:
  //   1. Map of coords to number filling that coordinate
  //   2. Set of coords for gears
  // Go through gears and sum gears adjacent to 2 numbers
  public long puzzle2() {
    Character[][] matrix = readMatrix();

    // First pass, find gears
    Set<Coord> gears = new HashSet<>();
    for (int row = 0; row < matrix.length; ++row) {
      for (int col = 0; col < matrix[row].length; ++col) {
        if (matrix[row][col] == '*') {
          gears.add(new Coord(row, col));
        }
      }
    }

    long sum = 0;
    Map<Coord, Integer> map = new HashMap<>();

    // Second pass, find numbers and map coordinates for each digit to number
    for (int row = 0; row < matrix.length; ++row) {
      for (int col = 0; col < matrix[row].length; ++col) {
        char c = matrix[row][col];

        if (Character.isDigit(c)) {
          int number = 0;
          List<Coord> coords = new ArrayList<>();

          while (Character.isDigit(c)) {
            coords.add(new Coord(row, col));
            number = number * 10 + Character.getNumericValue(c);
            if (col + 1 == matrix[row].length) {
              break;
            }
            c = matrix[row][++col];
          }

          for (var coord : coords) {
            map.put(coord, number);
          }
        }
      }
    }

    // Go through gears and find gear ratios for those adjacent to 2 numbers
    for (var coord : gears) {
      Set<Integer> adjacent = new HashSet<>();

      for (int[] move : getMoves()) {
        int y = coord.row() + move[0];
        int x = coord.col() + move[1];
        Coord adj = new Coord(y, x);

        if (map.containsKey(adj)) {
          adjacent.add(map.get(adj));
        }
      }

      if (adjacent.size() == 2) {
        sum += adjacent.stream().reduce(1, Math::multiplyExact);
      }
    }

    return sum;
  }

  List<int[]> getMoves() {
    return List.of(
        new int[] {-1, 0},  // up
        new int[] {1, 0},  // down
        new int[] {0, -1},  // left
        new int[] {0, 1},  // right
        new int[] {-1, -1},  // up-left
        new int[] {-1, 1},  // up-right
        new int[] {1, -1},  // down-left
        new int[] {1, 1}  // down-right
    );
  }

  boolean hasAdjacentSymbol(int row, int col, Character[][] matrix) {
    for (int[] move : getMoves()) {
      int y = row + move[0];
      int x = col + move[1];

      if (inBounds(y, x, matrix) &&
          isSymbol(y, x, matrix)) {
        return true;
      }
    }

    return false;
  }

  boolean inBounds(int row, int col, Character[][] matrix) {
    return row >= 0 && row < matrix.length &&
        col >= 0 && col < matrix[row].length;
  }

  boolean isSymbol(int row, int col, Character[][] matrix) {
    char c = matrix[row][col];
    return !Character.isDigit(c) && c != '.';
  }

  Character[][] readMatrix() {
    var iter = Resource.readLines("day03.input").iterator();

    List<String> lines = new ArrayList<>();
    while (iter.hasNext()) {
      lines.add(iter.next());
    }

    Character[][] matrix = new Character[lines.size()][lines.get(0).length()];

    for (int i = 0; i < lines.size(); ++i) {
      var line = lines.get(i);
      for (int j = 0; j < line.length(); ++j) {
        matrix[i][j] = line.charAt(j);
      }
    }

    return matrix;
  }
}
