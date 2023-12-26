package com.cadizm.aoc._2023;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.google.common.base.Preconditions;

// https://adventofcode.com/2023/day/12
public class Day12HotSprings {

  private record Condition(String record, List<Integer> groups) {
    @Override
    public String toString() {
      String g = groups.stream()
          .map(String::valueOf)
          .collect(Collectors.joining(","));

      return String.format("%s %s", record, g);
    }
  }

  private final List<Condition> conditions;
  private final Map<Condition, Long> memo;

  public Day12HotSprings(String inputPath) {
    this(inputPath, 1);
  }

  public Day12HotSprings(String inputPath, int foldFactor) {
    this.conditions = parseInput(inputPath, foldFactor);
    this.memo = new HashMap<>();
  }

  public long puzzle1() {
    long sum = 0;

    for (var condition : conditions) {
      sum += calculateArrangements(condition);
    }

    return sum;
  }

  public long puzzle2() {
    long sum = 0;

    for (var condition : conditions) {
      sum += calculateArrangements(condition);
    }

    return sum;
  }

  // TODO: describe logic
  // Approach adapted from
  // https://www.reddit.com/r/adventofcode/comments/18hbbxe/2023_day_12python_stepbystep_tutorial_with_bonus/
  long calculateArrangements(Condition condition) {
    if (memo.containsKey(condition)) {
      return memo.get(condition);
    }

    String record = condition.record;
    List<Integer> groups = condition.groups;

    if (groups.isEmpty()) {
      return !record.contains("#") ? 1 : 0;
    } else if (record.isEmpty()) {
      return 0;
    }

    long res = switch (record.charAt(0)) {
      case '#' -> handleDamaged(condition);
      case '.' -> handleOperational(condition);
      case '?' -> handleDamaged(condition) + handleOperational(condition);
      default -> throw new RuntimeException();
    };

    memo.put(condition, res);

    return res;
  }

  long handleDamaged(Condition condition) {
    Preconditions.checkArgument(!condition.groups.isEmpty());

    String record = condition.record;
    List<Integer> groups = condition.groups;

    int nextCount = groups.get(0);

    if (nextCount > record.length()) {
      return 0;
    }

    String cur = record.substring(0, nextCount);
    cur = cur.replaceAll("\\?", "#");

    if (!cur.equals("#".repeat(nextCount))) {
      return 0;
    } else if (record.length() == nextCount) {
      return groups.size() == 1 ? 1 : 0;
    }

    if (List.of('?', '.').contains(record.charAt(nextCount))) {
      record = record.substring(nextCount + 1);
      groups = groups.subList(1, groups.size());

      return calculateArrangements(new Condition(record, groups));
    }

    return 0;
  }

  // .
  long handleOperational(Condition condition) {
    Preconditions.checkArgument(!condition.record.isEmpty());

    String record = condition.record.substring(1);

    return calculateArrangements(new Condition(record, condition.groups));
  }

  // Return whether the arrangement is valid (only used for brute-force)
  boolean isValidArrangement(String record, List<Integer> groups) {
    String[] parts = record.replaceFirst("^(\\.)+", "").split("(\\.)+");

    if (parts.length != groups.size()) {
      return false;
    }

    for (int i = 0; i < parts.length; ++i) {
      if (parts[i].length() != groups.get(i)) {
        return false;
      }
    }

    return true;
  }

  // Brute-force method for generating all possible arrangements (not used)
  public List<String> generateRecordArrangements(Condition condition) {
    List<String> res = new ArrayList<>();

    Deque<String> queue = new ArrayDeque<>();

    queue.add(condition.record);

    while (!queue.isEmpty()) {
      String record = queue.remove();

      int index = record.indexOf("?");

      if (index == -1) {
        res.add(record);
        continue;
      }

      queue.add(record.replaceFirst("\\?", "."));
      queue.add(record.replaceFirst("\\?", "#"));
    }

    return res;
  }

  List<Condition> parseInput(String path, int foldFactor) {
    Stream<String> stream = Resource.readLines(path);

    List<Condition> conditions = new ArrayList<>();

    for (var iter = stream.iterator(); iter.hasNext(); ) {
      String[] parts = iter.next().split(" ");

      String record = parts[0];
      record = String.join("?", Collections.nCopies(foldFactor, record));

      String rawGroups = parts[1];
      rawGroups = String.join(",", Collections.nCopies(foldFactor, rawGroups));

      List<Integer> groups = Arrays.stream(rawGroups.split(","))
          .map(Integer::parseInt)
          .toList();

      conditions.add(new Condition(record, groups));
    }

    return conditions;
  }
}
