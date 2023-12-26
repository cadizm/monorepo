package com.cadizm.aoc._2023;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.google.common.base.Preconditions;

// https://adventofcode.com/2023/day/19
public class Day19Aplenty {

  enum State {
    ACCEPTED,
    REJECTED;

    public static State findByValue(String value) {
      return switch (value) {
        case "A" -> ACCEPTED;
        case "R" -> REJECTED;
        default -> null;
      };
    }
  }

  record Workflow(String name, List<Rule> rules, String defaultValue) {
    public String evaluate(Part part) {
      for (Rule rule : rules) {
        if (rule.apply(part)) {
          return rule.value;
        }
      }

      return defaultValue;
    }

    // Rule
    // px {
    //   a < 2006 : qkq
    //   m > 2090 : A
    //       rfg
    // }
    //
    // Input
    // PartRange(x: 1-4000, m: 1-4000, a: 1-4000, s:1-4000)
    //
    // Output
    // [
    //  (Value(qkq), PartRange(x: 1-4000, m: 1-4000, a: 1-2005, s:1-4000)),
    //  (Value(A),   PartRange(x: 1-4000, m: 2091-4000, a: 2006-4000, s:1-4000)),
    //  (Value(rfg), PartRange(x: 1-4000, m: 1-2090, a: 2006-4000, s:1-4000)),
    // ]
    public List<ValuePartRange> evaluate(PartRange partRange) {
      List<ValuePartRange> res = new ArrayList<>();

      for (Rule rule : rules) {
        PartRangeResult result = rule.apply(partRange);
        res.add(new ValuePartRange(rule.value, result.applied));
        partRange = result.unapplied;
      }

      res.add(new ValuePartRange(defaultValue, partRange));

      return res;
    }
  }

  record PartRangeResult(PartRange applied, PartRange unapplied) {}

  record Rule(String lhs, String operator, String rhs, String value) {
    public boolean apply(Part part) {
      return switch (operator) {
        case "<" -> part.get(lhs) < Integer.parseInt(rhs);
        case ">" -> part.get(lhs) > Integer.parseInt(rhs);
        default -> throw new RuntimeException();
      };
    }

    // Rule
    //   a < 2006 : qkq
    //
    // Input
    // PartRange(x: 1-4000, m: 1-4000, a: 1-4000, s:1-4000)
    //
    // Output
    // PartRangeResult(
    //   applied: PartRange(x: 1-4000, m: 1-4000, a: 1-2005, s:1-4000),
    //   unapplied: PartRange(x: 1-4000, m: 1-4000, a: 2006-4000, s:1-4000))
    public PartRangeResult apply(PartRange partRange) {
      Range lhsRange = partRange.get(lhs);

      RangeResult rangeResult = switch (operator) {
        case "<" -> lhsRange.lt(Integer.parseInt(rhs));
        case ">" -> lhsRange.gt(Integer.parseInt(rhs));
        default -> throw new RuntimeException();
      };

      return new PartRangeResult(
          partRange.update(lhs, rangeResult.applied),
          partRange.update(lhs, rangeResult.unapplied));
    }
  }

  record Part(int x, int m, int a, int s) {
    public int get(String value) {
      return switch (value) {
        case "x" -> x;
        case "m" -> m;
        case "a" -> a;
        case "s" -> s;
        default -> throw new RuntimeException();
      };
    }

    public int sum() {
      return x + m + a + s;
    }
  }

  record Range(int start, int end) {
    public Range {
      Preconditions.checkArgument(end >= start);
    }

    public int sum() {
      return end - start + 1;
    }

    // start = 1
    // end = 4000
    //
    // Input
    // n = 1351
    //
    // Output
    // RangeResult(
    //   Range(1, 1350),
    //   Range(1351, 4000)
    // )
    public RangeResult lt(int n) {
      Preconditions.checkArgument(n >= start && n <= end);

      Range a = new Range(start, n - 1);
      Range b = new Range(n, end);

      Preconditions.checkArgument(a.sum() + b.sum() == end - start + 1);

      return new RangeResult(a, b);
    }

    // start = 1
    // end = 4000
    //
    // Input
    // n = 1351
    //
    // Output
    // RangeResult(
    //   Range(1352, 4000)
    //   Range(1, 1351),
    // )
    public RangeResult gt(int n) {
      Preconditions.checkArgument(n >= start && n <= end);

      Range a = new Range(start, n);
      Range b = new Range(n + 1, end);

      Preconditions.checkArgument(a.sum() + b.sum() == end - start + 1);

      return new RangeResult(b, a);
    }
  }

  record RangeResult(Range applied, Range unapplied) {}

  record PartRange(Range x, Range m, Range a, Range s) {
    public Range get(String value) {
      return switch (value) {
        case "x" -> x;
        case "m" -> m;
        case "a" -> a;
        case "s" -> s;
        default -> throw new RuntimeException();
      };
    }

    public PartRange update(String field, Range r) {
      return switch (field) {
        case "x" -> new PartRange(r, m, a, s);
        case "m" -> new PartRange(x, r, a, s);
        case "a" -> new PartRange(x, m, r, s);
        case "s" -> new PartRange(x, m, a, r);
        default -> throw new RuntimeException();
      };
    }

    public long product() {
      return (long)x.sum() * m.sum() * a.sum() * s.sum();
    }
  }

  record ValuePartRange(String value, PartRange partRange) {}

  private final Map<String, Workflow> workflowMap;
  private final List<Part> parts;

  public Day19Aplenty(String inputPath) {
    this.workflowMap = readWorkflowMap(inputPath);
    this.parts = readParts(inputPath);
  }

  public long puzzle1() {
    List<Part> accepted = new ArrayList<>();

    for (Part part : parts) {
      Workflow workflow = workflowMap.get("in");
      String value = workflow.evaluate(part);

      while (isNonTerminalState(value)) {
        workflow = workflowMap.get(value);
        value = workflow.evaluate(part);
      }

      if (isAccepted(value)) {
        accepted.add(part);
      }
    }

    return sum(accepted);
  }

  public long puzzle2() {
    List<ValuePartRange> accepted = new ArrayList<>();

    Deque<ValuePartRange> queue = new ArrayDeque<>();
    queue.add(new ValuePartRange("in", new PartRange(
        new Range(1, 4000),
        new Range(1, 4000),
        new Range(1, 4000),
        new Range(1, 4000))));

    while (!queue.isEmpty()) {
      ValuePartRange valueRange = queue.remove();

      Workflow workflow = workflowMap.get(valueRange.value);
      List<ValuePartRange> valueRanges = workflow.evaluate(valueRange.partRange);

      queue.addAll(valueRanges.stream()
          .filter(vr -> isNonTerminalState(vr.value))
          .toList());

      accepted.addAll(valueRanges.stream()
          .filter(vr -> isAccepted(vr.value))
          .toList());
    }

    return product(accepted.stream()
        .map(ValuePartRange::partRange)
        .toList());
  }

  long product(List<PartRange> partRanges) {
    return partRanges.stream()
        .map(PartRange::product)
        .reduce(0L, Long::sum);
  }

  int sum(List<Part> parts) {
    return parts.stream()
        .map(Part::sum)
        .reduce(0, Integer::sum);
  }

  boolean isNonTerminalState(String value) {
    return State.findByValue(value) == null;
  }

  boolean isAccepted(String value) {
    return State.findByValue(value) == State.ACCEPTED;
  }

  Map<String, Workflow> readWorkflowMap(String path) {
    Stream<String> stream = Resource.readLines(path);

    Map<String, Workflow> map = new HashMap<>();

    for (var iter = stream.iterator(); iter.hasNext(); ) {
      String line = iter.next();

      // End of workflows, parts after blank line
      if (line.isBlank()) {
        break;
      }

      String regex = "^(?<name>\\w+)\\{(?<rules>.*?)}$";
      Pattern pattern = Pattern.compile(regex);
      Matcher matcher = pattern.matcher(line);

      if (!matcher.matches()) {
        throw new RuntimeException("No name-to-rule match");
      }

      String name = matcher.group("name");
      String[] ruleParts = matcher.group("rules").split(",");
      String defaultValue = ruleParts[ruleParts.length - 1];

      List<Rule> rules = new ArrayList<>();

      // Don't process last part, will be State or other Workflow value
      for (int i = 0; i < ruleParts.length - 1; ++i) {
        String s = ruleParts[i];
        String[] parts = s.split(":");
        Preconditions.checkArgument(parts.length == 2);

        String rawRule = parts[0];
        String value = parts[1];

        regex = "^(?<lhs>\\w+)(?<op>[<>])(?<rhs>\\d+)$";
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(rawRule);

        if (!matcher.matches()) {
          throw new RuntimeException("No rule decomposition match");
        }

        String lhs = matcher.group("lhs");
        String op = matcher.group("op");
        String rhs = matcher.group("rhs");

        rules.add(new Rule(lhs, op, rhs, value));
      }

      if (map.containsKey(name)) {
        throw new RuntimeException("Duplicate key " + name);
      }

      map.put(name, new Workflow(name, rules, defaultValue));
    }

    return map;
  }

  List<Part> readParts(String path) {
    Stream<String> stream = Resource.readLines(path);

    List<Part> res = new ArrayList<>();
    var iter = stream.iterator();

    // Skip workflows
    while (true) {
      if (iter.next().isBlank()) {
        break;
      }
    }

    while (iter.hasNext()) {
      String[] parts = iter.next().split(",");
      Preconditions.checkArgument(parts.length == 4);

      Part part = fromParts(
          Arrays.stream(parts)
              .map(s -> s.replaceAll("[{}]", ""))
              .map(s -> s.split("=")[1])
              .map(Integer::parseInt)
              .toList());

      res.add(part);
    }

    return res;
  }

  Part fromParts(List<Integer> parts) {
    Preconditions.checkArgument(parts.size() == 4);
    return new Part(parts.get(0), parts.get(1), parts.get(2), parts.get(3));
  }
}
