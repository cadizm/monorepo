package com.cadizm.aoc._2022;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cadizm.io.Resource;

/*
 * --- Day 8: Treetop Tree House ---
 * The expedition comes across a peculiar patch of tall trees all planted carefully in a grid. The
 * Elves explain that a previous expedition planted these trees as a reforestation effort. Now,
 * they're curious if this would be a good location for a tree house.
 *
 * First, determine whether there is enough tree cover here to keep a tree house hidden. To do this,
 * you need to count the number of trees that are visible from outside the grid when looking
 * directly along a row or column.
 *
 * The Elves have already launched a quadcopter to generate a map with the height of each tree (your
 * puzzle input). For example:
 *
 * 30373
 * 25512
 * 65332
 * 33549
 * 35390
 *
 * Each tree is represented as a single digit whose value is its height, where 0 is the shortest and
 * 9 is the tallest.
 *
 * A tree is visible if all of the other trees between it and an edge of the grid are shorter than
 * it. Only consider trees in the same row or column; that is, only look up, down, left, or right
 * from any given tree.
 *
 * All of the trees around the edge of the grid are visible - since they are already on the edge,
 * there are no trees to block the view. In this example, that only leaves the interior nine trees
 * to consider:
 *
 * The top-left 5 is visible from the left and top. (It isn't visible from the right or bottom since
 * other trees of height 5 are in the way.)
 * The top-middle 5 is visible from the top and right.
 * The top-right 1 is not visible from any direction; for it to be visible, there would need to only
 * be trees of height 0 between it and an edge.
 * The left-middle 5 is visible, but only from the right.
 * The center 3 is not visible from any direction; for it to be visible, there would need to be only
 * trees of at most height 2 between it and an edge.
 * The right-middle 3 is visible from the right.
 * In the bottom row, the middle 5 is visible, but the 3 and 4 are not.
 * With 16 trees visible on the edge and another 5 visible in the interior, a total of 21 trees are
 * visible in this arrangement.
 *
 * Consider your map; how many trees are visible from outside the grid?
 */
public class Day08TreeTopTreeHouse {

  public static int visibleTrees() {
    List<String> lines = Resource.readLines("day08.input")
        .collect(Collectors.toList());

    int rows = lines.size();
    int cols = lines.get(0).length();

    int[][] grid = new int[rows][cols];

    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < cols; ++j) {
        grid[i][j] = lines.get(i).charAt(j) - '0';
      }
    }

    int count = 0;

    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < cols; ++j) {
        if (isVisible(i, j, grid)) {
          count += 1;
        }
      }
    }

    return count;
  }

  public static int maxScenicScore() {
    List<String> lines = Resource.readLines("day08.input")
        .collect(Collectors.toList());

    int rows = lines.size();
    int cols = lines.get(0).length();

    int[][] grid = new int[rows][cols];

    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < cols; ++j) {
        grid[i][j] = lines.get(i).charAt(j) - '0';
      }
    }

    int max = 0;

    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < cols; ++j) {
        max = Math.max(max, scenicScore(i, j, grid));
      }
    }

    return max;
  }

  static int scenicScore(int row, int col, int[][] grid) {
    // get neighbors in each direction
    List<Integer> up = getAbove(row, col, grid);
    List<Integer> left = getLeft(row, col, grid);
    List<Integer> down = getBelow(row, col, grid);
    List<Integer> right = getRight(row, col, grid);

    return Stream.of(up, down, left, right)
        .map(neighbors -> viewingDistance(neighbors, grid[row][col]))
        .reduce(1, Math::multiplyExact);
  }

  static int viewingDistance(List<Integer> neighbors, int tree) {
    int count = 0;

    for (int neighbor : neighbors) {
      count += 1;
      if (neighbor >= tree) {
        break;
      }
    }

    return count;
  }

  static boolean isVisible(int row, int col, int[][] grid) {
    if (row == 0 || row == grid.length - 1 ||  // top or bottom rows
        col == 0 || col == grid[row].length - 1) {  // first or last columns
      return true;
    }

    // get neighbors in each direction
    List<Integer> up = getAbove(row, col, grid);
    List<Integer> down = getBelow(row, col, grid);
    List<Integer> left = getLeft(row, col, grid);
    List<Integer> right = getRight(row, col, grid);

    // tree is visible from a direction if taller than neighbors in that direction
    Predicate<List<Integer>> visible = list -> list.stream()
        .allMatch(n -> n < grid[row][col]);

    // a tree is visible if it is visible from at least 1 direction
    return Stream.of(up, down, left, right)
        .anyMatch(visible);
  }

  static List<Integer> getAbove(int row, int col, int[][] grid) {
    List<Integer> res = new ArrayList<>();

    for (int i = row - 1; i >= 0; --i) {
      res.add(grid[i][col]);
    }

    return res;
  }

  static List<Integer> getBelow(int row, int col, int[][] grid) {
    List<Integer> res = new ArrayList<>();

    for (int i = row + 1; i < grid.length; ++i) {
      res.add(grid[i][col]);
    }

    return res;
  }

  static List<Integer> getLeft(int row, int col, int[][] grid) {
    List<Integer> res = new ArrayList<>();

    for (int j = col - 1; j >= 0; --j) {
      res.add(grid[row][j]);
    }

    return res;
  }

  static List<Integer> getRight(int row, int col, int[][] grid) {
    List<Integer> res = new ArrayList<>();

    for (int j = col + 1; j < grid[row].length; ++j) {
      res.add(grid[row][j]);
    }

    return res;
  }

  static void print(int[][] grid) {
    for (int i = 0; i < grid.length; ++i) {
      for (int j = 0; j < grid[i].length; ++j) {
        System.out.print(String.format("%d", grid[i][j]));
      }
      System.out.println();
    }
  }
}
