package com.cadizm.aoc._2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.google.common.base.Preconditions;

// https://adventofcode.com/2023/day/9
public class Day09MirageMaintenance {

  enum Direction {
    FORWARD,
    BACKWARD
  }

  private final List<List<Integer>> history;

  public Day09MirageMaintenance(String inputPath) {
    Stream<String> stream = Resource.readLines(inputPath);

    history = new ArrayList<>();

    for (var iter = stream.iterator(); iter.hasNext(); ) {
      String[] parts = iter.next().split("\\s+");
      history.add(Arrays.stream(parts)
          .map(Integer::valueOf)
          .toList());
    }
  }

  // Recursively calculate each line's extrapolated last value and return their sum
  public int puzzle1() {
    int sum = 0;

    for (var values : history) {
      sum += extrapolate(values, Direction.FORWARD);
    }

    return sum;
  }

  // Recursively calculate each line's extrapolated first value and return their sum
  public int puzzle2() {
    int sum = 0;

    for (var values : history) {
      sum += extrapolate(values, Direction.BACKWARD);
    }

    return sum;
  }

  int extrapolate(List<Integer> values, Direction direction) {
    Preconditions.checkArgument(values.size() > 1);

    List<Integer> differences = new ArrayList<>();

    boolean flag = true;  // all differences are 0
    for (int i = 1; i < values.size(); ++i) {
      int value = values.get(i) - values.get(i - 1);

      if (value != 0) {
        flag = false;
      }

      differences.add(i - 1, value);
    }

    int firstValue = values.get(0);
    int lastValue = values.get(values.size() - 1);

    return switch (direction) {
      case FORWARD -> lastValue + (flag ? 0 : extrapolate(differences, direction));
      case BACKWARD -> firstValue - (flag ? 0 : extrapolate(differences, direction));
    };
  }
}
