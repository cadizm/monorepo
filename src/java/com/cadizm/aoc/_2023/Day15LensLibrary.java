package com.cadizm.aoc._2023;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.google.common.base.Preconditions;

// https://adventofcode.com/2023/day/15
public class Day15LensLibrary {

  private final List<String> steps;

  public Day15LensLibrary(String inputPath) {
    this.steps = readInput(inputPath);
  }

  public long puzzle1() {
    long sum = 0;

    for (String s : steps) {
      sum += hash(s);
    }

    return sum;
  }

  public long puzzle2() {
    long sum = 0;

    return sum;
  }

  public int hash(String s) {
    int val = 0;

    for (char c : s.toCharArray()) {
      val += c;
      val *= 17;
      val = val % 256;
    }

    return val;
  }

  List<String> readInput(String path) {
    Stream<String> stream = Resource.readLines(path);
    var iter = stream.iterator();

    String line = iter.next();

    Preconditions.checkArgument(!iter.hasNext());

    return Arrays.stream(line.split(",")).toList();
  }
}
