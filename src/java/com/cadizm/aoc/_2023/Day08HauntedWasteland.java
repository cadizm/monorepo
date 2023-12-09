package com.cadizm.aoc._2023;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.cadizm.math.Maths;

// https://adventofcode.com/2023/day/8
public class Day08HauntedWasteland {

  enum Instruction {
    L,
    R
  }

  record Pair(String left, String right) {}

  private final Map<Integer, Instruction> instructions;
  private final Map<String, Pair> map;

  public Day08HauntedWasteland(String inputPath) {
    Stream<String> stream = Resource.readLines(inputPath);
    var iter = stream.iterator();

    this.instructions = parseInstructions(iter.next());
    this.map = parseMap(iter);
  }

  // Part 1 is a straightforward implementation of the problem using the input
  // map and the list of "instructions": start at AAA and count the number of
  // steps required to reach ZZZ.
  public int puzzle1() {
    String key = "AAA";
    Predicate<String> predicate = s -> s.equals("ZZZ");

    return distance(key, predicate);
  }

  // The key insight into solving part 2 is to realize that the number of steps
  // required to reach "all keys ending with Z simultaneously", starting from
  // "all keys starting with A" is the LCM of each of the disances from a given
  // key starting with A to a key ending with Z.
  public BigInteger puzzle2() {
    List<String> keys = map.keySet()
        .stream()
        .filter(k -> k.endsWith("A"))
        .toList();

    Predicate<String> predicate = s -> s.endsWith("Z");

    List<Integer> steps = new ArrayList<>();
    for (var key : keys) {
      steps.add(distance(key, predicate));
    }

    return Maths.lcm(steps);
  }

  /**
   * Return the number of steps taken to reach a key in `map` satisfying `predicate`.
   *
   * Paths start at `key` and move to subsequent keys based on the lookup value of
   * `key` in `map`. Use the current instruction to determine whether to use `left`
   * or `right` of current value.
   */
  int distance(String key, Predicate<String> predicate) {
    int count = 0;

    int index = 0;
    while (true) {
      count += 1;

      Pair pair = map.get(key);
      var instruction = instructions.get(index);

      key = switch (instruction) {
        case L -> pair.left;
        case R -> pair.right;
      };

      if (predicate.test(key)) {
        break;
      }

      index = (index + 1) % instructions.size();
    }

    return count;
  }

  Map<Integer, Instruction> parseInstructions(String line) {
    String[] parts = line.split("");

    Map<Integer, Instruction> map = new HashMap<>();

    for (int i = 0; i < parts.length; ++i) {
      map.put(i, Instruction.valueOf(parts[i]));
    }

    return map;
  }

  public Map<String, Pair> parseMap(Iterator<String> iter) {
    Map<String, Pair> map = new HashMap<>();

    while (iter.hasNext()) {
      String line = iter.next();
      if (line.isEmpty()) {
        continue;
      }

      String[] parts = line.split(" = ");
      String key = parts[0];
      Pair pair = parsePair(parts[1]);

      map.put(key, pair);
    }

    return map;
  }

  Pair parsePair(String s) {
    s = s.replaceAll("[()]", "");
    String[] parts = s.split(", ");

    return new Pair(parts[0], parts[1]);
  }
}
