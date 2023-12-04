package com.cadizm.aoc._2023;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.cadizm.io.Resource;

// https://adventofcode.com/2023/day/4
public class Day04Scratchcards {

  // Process input line-by-line
  // Split on `:`, then on `|` to get "winning" and "have"
  // Find intersection of winning and have, n
  // Calculate points for card: shift 1 left n - 1 times
  // Return total points
  public int puzzle1() {
    Stream<String> stream = Resource.readLines("day04.input");

    int sum = 0;

    for (var iter = stream.iterator(); iter.hasNext(); ) {
      String line = iter.next();
      String[] parts = line.split(": ");
      parts = parts[1].split(" \\| ");

      List<Integer> winning = parseNumbers(parts[0]);
      List<Integer> have = parseNumbers(parts[1]);

      int n = intersection(winning, have).size();
      sum += n > 0 ? 1 << (n - 1) : 0;
    }

    return sum;
  }

  Set<Integer> intersection(Collection<Integer> a, Collection<Integer> b) {
    var set = new HashSet<>(a);
    set.retainAll(new HashSet<>(b));

    return set;
  }

  List<Integer> parseNumbers(String line) {
    return Arrays.stream(line.split("\\s+"))
        .filter(s -> !s.isBlank())
        .map(Integer::parseInt)
        .toList();
  }

  public long puzzle2() {
    return 0;
  }
}
