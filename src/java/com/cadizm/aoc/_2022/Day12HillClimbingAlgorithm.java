package com.cadizm.aoc._2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/*
 * --- Day 12: Hill Climbing Algorithm ---
 * You try contacting the Elves using your handheld device, but the river you're following must be
 * too low to get a decent signal.
 *
 * You ask the device for a heightmap of the surrounding area (your puzzle input). The heightmap
 * shows the local area from above broken into a grid; the elevation of each square of the grid is
 * given by a single lowercase letter, where a is the lowest elevation, b is the next-lowest, and so
 * on up to the highest elevation, z.
 *
 * Also included on the heightmap are marks for your current position (S) and the location that
 * should get the best signal (E). Your current position (S) has elevation a, and the location that
 * should get the best signal (E) has elevation z.
 *
 * You'd like to reach E, but to save energy, you should do it in as few steps as possible. During
 * each step, you can move exactly one square up, down, left, or right. To avoid needing to get out
 * your climbing gear, the elevation of the destination square can be at most one higher than the
 * elevation of your current square; that is, if your current elevation is m, you could step to
 * elevation n, but not to elevation o. (This also means that the elevation of the destination
 * square can be much lower than the elevation of your current square.)
 *
 * For example:
 *
 * Sabqponm
 * abcryxxl
 * accszExk
 * acctuvwj
 * abdefghi
 *
 * Here, you start in the top-left corner; your goal is near the middle. You could start by moving
 * down or right, but eventually you'll need to head toward the E at the bottom. From there, you can
 * spiral around to the goal:
 *
 * v..v<<<<
 * >v.vv<<^
 * .>vv>E^^
 * ..v>>>^^
 * ..>>>>>^
 *
 * In the above diagram, the symbols indicate whether the path exits each square moving up (^), down
 * (v), left (<), or right (>). The location that should get the best signal is still E, and . marks
 * unvisited squares.
 *
 * This path reaches the goal in 31 steps, the fewest possible.
 *
 * What is the fewest steps required to move from your current position to the location that should
 * get the best signal?
 */
public class Day12HillClimbingAlgorithm {

  public static int fewestStepsFromGivenStart() {
    Stream<String> lines = Resource.readLines("day12.input");

    HillClimber hillClimber = new HillClimber(lines);
    List<Position> startPositions = List.of(hillClimber.locateStart());

    return hillClimber.run(startPositions);
  }

  public static int fewestStepsFromAnyStart() {
    Stream<String> lines = Resource.readLines("day12.input");

    HillClimber hillClimber = new HillClimber(lines);
    List<Position> startPositions = hillClimber.locateStartPositions();

    return hillClimber.run(startPositions);
  }

  static class HillClimber {
    final Stream<String> input;
    final char[][] grid;

    public HillClimber(Stream<String> input) {
      this.input = input;
      this.grid = parseGrid(input);
    }

    public int run(List<Position> startPositions) {
      List<Integer> pathLengths = new ArrayList<>();

      for (Position start : startPositions) {
        try {
          pathLengths.add(bfs(start));
        } catch (RuntimeException e) {
          // System.out.println(String.format("No path from %s", start));
        }
      }

      return pathLengths.stream()
          .min(Integer::compare)
          .orElseThrow(RuntimeException::new);
    }

    int bfs(Position root) {
      assert root != null;

      Deque<Position> path = new ArrayDeque<>();
      Set<Position> visited = new HashSet<>();

      path.offer(new Position(root.row, root.col, 0));

      while (!path.isEmpty()) {
        Position pos = path.poll();

        if (grid[pos.row][pos.col] == 'E') {
          return pos.depth;
        }

        for (var neighbor : climbableNeighbors(pos)) {
          if (!visited.contains(neighbor)) {
            visited.add(neighbor);
            path.offer(new Position(neighbor.row, neighbor.col, pos.depth + 1));
          }
        }
      }

      throw new RuntimeException("Couldn't find path to 'E'");
    }

    List<Position> climbableNeighbors(Position cur) {
      assert cur != null && isValid(cur);

      List<Position> res = new ArrayList<>();

      List<Position> deltas = List.of(
          new Position(-1, 0),   // up
          new Position(1, 0),   // down
          new Position(0, -1),   // left
          new Position(0, 1));  // right

      for (var delta : deltas) {
        var pos = new Position(cur.row + delta.row, cur.col + delta.col);
        if (isValid(pos) && isClimbable(cur, pos)) {
          res.add(pos);
        }
      }

      return res;
    }

    boolean isValid(Position pos) {
      return pos.row >= 0 && pos.row < grid.length &&
          pos.col >= 0 && pos.col < grid[pos.row].length;
    }

    // return true if b is climbable from a, false otherwise
    boolean isClimbable(Position a, Position b) {
      assert a != null && b != null;
      assert isValid(a) && isValid(b);

      char c1 = grid[a.row][a.col];
      char c2 = grid[b.row][b.col];

      if (c1 == 'S') {
        c1 = 'a';
      } else if (c1 == 'E') {
        c1 = 'z';
      }

      if (c2 == 'S') {
        c2 = 'a';
      } else if (c2 == 'E') {
        c2 = 'z';
      }

      assert c1 >= 'a' && c1 <= 'z';
      assert c2 >= 'a' && c2 <= 'z';

      int h1 = c1 - 'a' + 1;
      int h2 = c2 - 'a' + 1;

      return h2 - 1 <= h1;
    }

    List<Position> locateStartPositions() {
      List<Position> positions = new ArrayList<>();

      for (int i = 0; i < grid.length; ++i) {
        for (int j = 0; j < grid[i].length; ++j) {
          if (grid[i][j] == 'S' || grid[i][j] == 'a') {
            positions.add(new Position(i, j));
          }
        }
      }

      return positions;
    }

    Position locateStart() {
      for (int i = 0; i < grid.length; ++i) {
        for (int j = 0; j < grid[i].length; ++j) {
          if (grid[i][j] == 'S') {
            return new Position(i, j);
          }
        }
      }

      throw new RuntimeException("Unable to locate start position");
    }

    char[][] parseGrid(Stream<String> input) {
      assert input != null;

      List<String> lines = input.collect(Collectors.toList());

      int rows = lines.size();
      int cols = lines.get(0).length();
      char[][] grid = new char[rows][cols];

      for (int i = 0; i < rows; ++i) {
        assert lines.get(i).length() == cols;
        for (int j = 0; j < cols; ++j) {
          grid[i][j] = lines.get(i).charAt(j);
        }
      }

      return grid;
    }

    void printGrid() {
      for (int i = 0; i < grid.length; ++i) {
        for (int j = 0; j < grid[i].length; ++j) {
          System.out.print(grid[i][j]);
        }
        System.out.println();
      }
    }
  }

  static class Position {
    final int row;
    final int col;
    final int depth;

    public Position(int row, int col) {
      this(row, col, 0);
    }

    public Position(int row, int col, int depth) {
      this.row = row;
      this.col = col;
      this.depth = depth;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }

      if (!(obj instanceof Position)) {
        return false;
      }

      Position other = (Position)obj;

      return this.row == other.row &&
          this.col == other.col;
    }

    @Override
    public int hashCode() {
      return new HashCodeBuilder(17, 31)
          .append(row)
          .append(col)
          .hashCode();
    }

    @Override
    public String toString() {
      return String.format("(%s, %s) d=%s", row, col, depth);
    }
  }
}
