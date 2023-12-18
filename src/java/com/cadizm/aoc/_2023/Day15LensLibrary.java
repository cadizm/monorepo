package com.cadizm.aoc._2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.google.common.base.Preconditions;

// https://adventofcode.com/2023/day/15
public class Day15LensLibrary {

  record Lens(String label, int focalLength) {}

  record Slot(int index, Lens lens) {}

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

    Set<String> seen = new HashSet<>();

    // Box -> List<Lens>
    Map<Integer, List<Lens>> map = new HashMap<>();

    // Store all initialization steps in map
    for (String step : steps) {
      String[] parts = step.split("[-=]");

      String label = parts[0];
      seen.add(label);

      int box = hash(label);
      List<Lens> lenses = map.computeIfAbsent(box, k -> new ArrayList<>());

      if (parts.length == 1) {
        remove(label, lenses);
      } else if (parts.length == 2) {
        int focalLength = Integer.parseInt(parts[1]);
        Lens lens = new Lens(label, focalLength);
        put(lens, lenses);
      } else {
        throw new RuntimeException("Invalid step: " + step);
      }
    }

    // Compute focusing power
    for (String label : seen) {
      int box = hash(label);
      List<Lens> lenses = map.get(box);
      Slot slot = get(label, lenses);

      if (slot != null) {
        sum += (box + 1L) * (slot.index + 1L) * slot.lens.focalLength;
      }
    }

    return sum;
  }

  Slot get(String label, List<Lens> lenses) {
    for (int i = 0; i < lenses.size(); ++i) {
      Lens lens = lenses.get(i);
      if (lens.label.equals(label)) {
        return new Slot(i, lens);
      }
    }
    return null;
  }

  void remove(String label, List<Lens> lenses) {
    for (var lens : lenses) {
      if (lens.label.equals(label)) {
        lenses.remove(lens);
        break;
      }
    }
  }

  void put(Lens lens, List<Lens> lenses) {
    for (int i = 0; i < lenses.size(); ++i) {
      Lens existing = lenses.get(i);
      if (existing.label.equals(lens.label)) {
        lenses.remove(i);
        lenses.add(i, lens);
        return;
      }
    }
    lenses.add(lens);
  }

  int hash(String s) {
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
