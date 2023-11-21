package com.cadizm.aoc._2022;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import static com.cadizm.io.Resource.readLines;

public class Day14RegolithReservoir {

  public static int restingSandUnits() {
    return new SandSimulation(readLines("day14.input"), false).run();
  }

  public static int restingSandUnitsWithFloor() {
    return new SandSimulation(readLines("day14.input"), true).run();
  }

  static class SandSimulation {
    final Stream<String> input;
    final List<CoordPair> rockPaths;
    final Set<Coord> taken;

    // rock path bounds
    final int maxRow;
    final int leftMost;
    final int rightMost;

    final boolean withFloor;  // part 2

    public SandSimulation(Stream<String> input, boolean withFloor) {
      this.input = input;
      this.rockPaths = new ArrayList<>();
      this.taken = new HashSet<>();
      this.withFloor = withFloor;

      for (var it = input.iterator(); it.hasNext(); ) {
        List<CoordPair> coordPairs = parseCoordPairs(it.next());
        rockPaths.addAll(coordPairs);
      }

      fillRockPaths();

      this.maxRow = taken.stream()
          .map(Coord::getRow)
          .max(Integer::compareTo)
          .orElseThrow(RuntimeException::new) + (withFloor ? 2 : 0);

      this.leftMost = taken.stream()
          .map(Coord::getCol)
          .min(Integer::compare)
          .orElseThrow(RuntimeException::new);

      this.rightMost = taken.stream()
          .map(Coord::getCol)
          .max(Integer::compare)
          .orElseThrow(RuntimeException::new);
    }

    int run() {
      int count = 0;

      while (true) {
        Coord sand = new Coord(0, 500);
        Coord restingPlace = findRestingPlace(sand);

        if (withFloor && restingPlace.equals(new Coord(0, 500))) {
          return count + 1;
        }

        if (!withFloor && restingPlace == null) {
          return count;
        }

        taken.add(restingPlace);
        count += 1;
      }
    }

    Coord findRestingPlace(Coord sand) {
      assert sand != null;

      if (!withFloor && !withinBounds(sand)) {
        return null;
      }

      if (canMoveDown(sand)) {
        return findRestingPlace(moveDown(sand));
      } else if (canMoveDownLeft(sand)) {
        return findRestingPlace(moveDownLeft(sand));
      } else if (canMoveDownRight(sand)) {
        return findRestingPlace(moveDownRight(sand));
      }

      return sand;
    }

    boolean withinBounds(Coord coord) {
      return coord.row < maxRow && coord.col >= leftMost && coord.col <= rightMost;
    }

    boolean isEmpty(Coord coord) {
      if (!withFloor) {
        return !taken.contains(coord);
      }

      return coord.row < maxRow && !taken.contains(coord);
    }

    boolean canMoveDown(Coord coord) {
      return isEmpty(moveDown(coord));
    }

    boolean canMoveDownLeft(Coord coord) {
      return isEmpty(moveDownLeft(coord));
    }

    boolean canMoveDownRight(Coord coord) {
      return isEmpty(moveDownRight(coord));
    }

    Coord moveDown(Coord cur) {
      return new Coord(cur.row + 1, cur.col);
    }

    Coord moveDownLeft(Coord cur) {
      return new Coord(cur.row + 1, cur.col - 1);
    }

    Coord moveDownRight(Coord cur) {
      return new Coord(cur.row + 1, cur.col + 1);
    }

    void fillRockPaths() {
      for (var coordPair : rockPaths) {
        Coord src = coordPair.src;
        Coord dst = coordPair.dst;

        if (src.row == dst.row) {   // fill horizontally
          int row = src.row;
          if (src.col < dst.col) {  // fill left -> right
            for (int col = src.col; col <= dst.col; ++col) {
              taken.add(new Coord(row, col));
            }
          } else {                  // fill right -> left
            for (int col = src.col; col >= dst.col; --col) {
              taken.add(new Coord(row, col));
            }
          }
        } else if (src.col == dst.col) {  // fill vertically
          int col = src.col;
          if (src.row < dst.row) {        // fill top -> bottom
            for (int row = src.row; row <= dst.row; ++row) {
              taken.add(new Coord(row, col));
            }
          } else {                        // fill bottom -> top
            for (int row = src.row; row >= dst.row; --row) {
              taken.add(new Coord(row, col));
            }
          }
        } else {
          throw new RuntimeException("Unable to find direction to fill");
        }
      }
    }

    List<CoordPair> parseCoordPairs(String s) {
      String[] parts = s.split(" -> ");

      List<CoordPair> coordPairs = new ArrayList<>();

      for (int i = 1; i < parts.length; ++i) {
        Coord src = new Coord(parts[i - 1]);
        Coord dst = new Coord(parts[i]);
        coordPairs.add(new CoordPair(src, dst));
      }

      return coordPairs;
    }
  }

  static class Coord {
    final int row;
    final int col;

    public Coord(String s) {
      this(s.trim().split(","));
    }

    // x = distance right (col)
    // y = distance down  (row)
    public Coord(String[] xy) {
      this(Integer.parseInt(xy[1]), Integer.parseInt(xy[0]));
    }

    public Coord(int row, int col) {
      this.row = row;
      this.col = col;
    }

    public int getRow() {
      return row;
    }

    public int getCol() {
      return col;
    }

    @Override
    public String toString() {
      return String.format("(%s,%s)", col, row);
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      }

      if (!(obj instanceof Coord)) {
        return false;
      }

      Coord other = (Coord)obj;

      return this.row == other.row && this.col == other.col;
    }

    @Override
    public int hashCode() {
      return new HashCodeBuilder(17, 31)
          .append(row)
          .append(col)
          .toHashCode();
    }
  }

  static class CoordPair {
    final Coord src;
    final Coord dst;

    public CoordPair(Coord src, Coord dst) {
      this.src = src;
      this.dst = dst;
    }

    @Override
    public String toString() {
      return String.format("%s -> %s", src, dst);
    }
  }
}
