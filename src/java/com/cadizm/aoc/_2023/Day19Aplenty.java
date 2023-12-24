package com.cadizm.aoc._2023;

import java.util.ArrayList;
import java.util.Arrays;
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
  }

  record Rule(String lhs, String operator, String rhs, String value) {
    public boolean apply(Part part) {
      return switch (operator) {
        case "<" -> part.get(lhs) < Integer.parseInt(rhs);
        case ">" -> part.get(lhs) > Integer.parseInt(rhs);
        default -> throw new RuntimeException();
      };
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

      while (!isTerminalState(value)) {
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
    return 0;
  }

  int sum(List<Part> parts) {
    return parts.stream()
        .map(Part::sum)
        .reduce(0, Integer::sum);
  }

  boolean isTerminalState(String value) {
    return State.findByValue(value) != null;
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
              .map(s -> s.replaceAll("[]\\{}]", ""))
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
