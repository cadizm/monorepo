package com.cadizm.aoc._2023;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.google.common.base.Preconditions;

// https://adventofcode.com/2023/day/6
public class Day06WaitForIt {

  record Race(int time, int distance) {}

  private final List<Race> races;

  public Day06WaitForIt() {
    Stream<String> stream = Resource.readLines("day06.input");
    var iter = stream.iterator();

    List<Integer> times = parseLine(iter.next());
    List<Integer> distances = parseLine(iter.next());

    Preconditions.checkArgument(times.size() == distances.size());

    races = new ArrayList<>();
    for (int i = 0; i < times.size(); ++i) {
      races.add(new Race(times.get(i), distances.get(i)));
    }
  }

  // Straightforward brute-force approach to calculate number of ways to win
  public int puzzle1() {
    int marginOfError = 1;

    for (var race : races) {
      int time = race.time();
      int distance = race.distance();

      int ways = 0;
      for (int i = 1; i < time; ++i) {
        int traveled = i * (time - i);
        boolean won = traveled > distance;
        ways += won ? 1 : 0;
      }

      marginOfError *= ways;
    }

    return marginOfError;
  }

  // Same approach as part 1, but use BigInteger to avoid possible overflow.
  // A closed-form solution would be to use the quadratic formula. See this
  // Reddit comment:
  //   https://www.reddit.com/r/adventofcode/comments/18bwe6t/comment/kc72otz/
  public BigInteger puzzle2() {
    BigInteger time = getCombinedRaceField(Race::time);
    BigInteger distance = getCombinedRaceField(Race::distance);

    var ways = BigInteger.ZERO;
    var i = BigInteger.ZERO;
    while (i.compareTo(time) < 0) {
      var traveled = i.multiply(time.subtract(i));
      boolean won = traveled.compareTo(distance) > 0;
      ways = ways.add(won ? BigInteger.ONE : BigInteger.ZERO);

      i = i.add(BigInteger.ONE);
    }

    return ways;
  }

  List<Integer> parseLine(String line) {
    String[] parts = line.split(".*?:\\s+");
    parts = parts[1].split("\\s+");

    return Arrays.stream(parts)
        .filter(s -> !s.isBlank())
        .map(Integer::parseInt)
        .toList();
  }

  BigInteger getCombinedRaceField(Function<Race, Integer> raceMethodRef) {
    String digits = races.stream()
        .map(raceMethodRef)
        .map(String::valueOf)
        .collect(Collectors.joining());

    return new BigInteger(digits);
  }
}
