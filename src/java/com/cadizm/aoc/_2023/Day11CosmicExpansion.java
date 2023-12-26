package com.cadizm.aoc._2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.cadizm.math.Maths;

// https://adventofcode.com/2023/day/11
public class Day11CosmicExpansion {

  record Galaxy(int id, int row, int col) {}

  private final String[][] grid;

  private final Map<Integer, Galaxy> galaxies;
  private final TreeSet<Integer> emptyRows;
  private final TreeSet<Integer> emptyCols;

  public Day11CosmicExpansion(String inputPath) {
    this.grid = readInput(inputPath);
    this.galaxies = getGalaxies();
    this.emptyRows = getEmptySpaceRows();
    this.emptyCols = getEmptySpaceCols();
  }

  // Calculate the manhattan distance between each pair of galaxies after
  // expanding each row and column of "empty space" by a factor of 2.
  public long puzzle1() {
    return sumOfGalaxyDistances(2);
  }

  // Calculate the manhattan distance between each pair of galaxies after
  // expanding each row and column of "empty space" by a factor of `factor`.
  public long puzzle2(long factor) {
    return sumOfGalaxyDistances(factor);
  }

  long sumOfGalaxyDistances(long factor) {
    long sum = 0L;

    for (int i = 1; i <= galaxies.size(); ++i) {
      for (int j = i + 1; j <= galaxies.size(); ++j) {
        Galaxy a = galaxies.get(i);
        Galaxy b = galaxies.get(j);

        int y1 = Math.min(a.row, b.row);
        int y2 = Math.max(a.row, b.row);

        int x1 = Math.min(a.col, b.col);
        int x2 = Math.max(a.col, b.col);

        int emptyRows = getEmptyRowsBetween(a, b).size();
        int emptyCols = getEmptyColsBetween(a, b).size();

        long yOffset = factor * emptyRows - emptyRows;
        long xOffset = factor * emptyCols - emptyCols;

        long distance = Maths.manhattanDistance(x1, y1, xOffset + x2, yOffset + y2);

        sum += distance;
      }
    }

    return sum;
  }

  Set<Integer> getEmptyRowsBetween(Galaxy a, Galaxy b) {
    return emptyRows.subSet(Math.min(a.row, b.row), Math.max(a.row, b.row));
  }

  Set<Integer> getEmptyColsBetween(Galaxy a, Galaxy b) {
    return emptyCols.subSet(Math.min(a.col, b.col), Math.max(a.col, b.col));
  }

  Map<Integer, Galaxy> getGalaxies() {
    if (galaxies != null) {
      return galaxies;
    }

    Map<Integer, Galaxy> galaxies = new HashMap<>();

    int id = 0;
    for (int row = 0; row < grid.length; ++row) {
      for (int col = 0; col < grid[row].length; ++col) {
        if (grid[row][col].equals("#")) {
          id += 1;
          galaxies.put(id, new Galaxy(id, row, col));
        }
      }
    }

    return galaxies;
  }

  TreeSet<Integer> getEmptySpaceRows() {
    if (emptyRows != null) {
      return emptyRows;
    }

    Set<Integer> galaxyRows = getGalaxies().values()
        .stream()
        .map(Galaxy::row)
        .collect(Collectors.toSet());

    Set<Integer> rows = IntStream.range(0, grid.length)
        .boxed()
        .collect(Collectors.toSet());

    rows.removeAll(galaxyRows);

    return new TreeSet<>(rows);
  }

  TreeSet<Integer> getEmptySpaceCols() {
    if (emptyCols != null) {
      return emptyCols;
    }

    Set<Integer> galaxyCols = getGalaxies().values()
        .stream()
        .map(Galaxy::col)
        .collect(Collectors.toSet());

    Set<Integer> cols = IntStream.range(0, grid[0].length)
        .boxed()
        .collect(Collectors.toSet());

    cols.removeAll(galaxyCols);

    return new TreeSet<>(cols);
  }

  String[][] readInput(String path) {
    Stream<String> stream = Resource.readLines(path);

    List<String[]> lines = new ArrayList<>();

    for (var iter = stream.iterator(); iter.hasNext(); ) {
      lines.add(iter.next().split(""));
    }

    int rows = lines.size();
    int cols = lines.get(0).length;

    String[][] grid = new String[rows][cols];

    for (int row = 0; row < rows; ++row) {
      for (int col = 0; col < cols; ++col) {
        grid[row][col] = lines.get(row)[col];
      }
    }

    return grid;
  }
}
