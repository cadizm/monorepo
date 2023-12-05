package com.cadizm.aoc._2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.google.common.base.Preconditions;

// https://adventofcode.com/2023/day/5
public class Day05IfYouGiveSeedFertilizer {
  // RangeMapping representing mapping of range from:
  //   [start, start + len)
  // to
  //   [offset - start, offset - start + len)
  // To calculate mapped value, given a source number within the mapping's range,
  // add the RangeMapping's offset to get the mapped destination value.
  // Numbers outside of range are not mapped, with destination equal to source.
  record RangeMapping(long start, long len, long offset) {}

  private List<Long> seeds;

  // Map of start -> RangeMapping
  private List<TreeMap<Long, RangeMapping>> maps;

  public Day05IfYouGiveSeedFertilizer() {
    Stream<String> stream = Resource.readLines("day05.input");

    for (var iter = stream.iterator(); iter.hasNext(); ) {
      this.seeds = parseSeeds(iter.next());
      this.maps = parseMaps(iter);
    }
  }

  // Parse input into list of seeds and List<TreeMap>
  // For each seed, traverse list of maps, applying mapping of each map
  // Return the last mapping, (i.e. the location)
  // We want to find the location with the lowest value given our list of seeds
  // Assumption here is that input range source range of intervals does not overlap
  public long puzzle1() {
    long min = Long.MAX_VALUE;

    for (long seed : seeds) {
      long key = seed;

      for (var map : maps) {
        var entry = map.floorEntry(key);

        if (entry == null) {
          // no mapping exists, retain key and move onto next map
          continue;
        }

        var mapping = entry.getValue();
        long start = mapping.start();
        long len = mapping.len();

        if (key >= start && key < start + len) {
          key += mapping.offset();
        }
      }

      min = Math.min(min, key);
    }

    return min;
  }

  // Part 2 is the same as part 1, except that we now consider ranges of seeds
  // TODO: figure out how to optimize
  public long puzzle2() {
    Preconditions.checkArgument(seeds.size() % 2 == 0);

    long min = Long.MAX_VALUE;

    for (int i = 0; i < seeds.size(); i += 2) {
      long seed = seeds.get(i);
      long end = seed + seeds.get(i + 1);

      for (; seed < end; ++seed) {
        long key = seed;

        for (var map : maps) {
          var entry = map.floorEntry(key);

          if (entry == null) {
            // no mapping exists, retain key and move onto next map
            continue;
          }

          var mapping = entry.getValue();
          long start = mapping.start();
          long len = mapping.len();

          if (key >= start && key < start + len) {
            key += mapping.offset();
          }
        }

        min = Math.min(min, key);
      }
    }

    return min;
  }

  List<Long> parseSeeds(String line) {
    String[] parts = line.split(": ");
    parts = parts[1].split("\\s+");

    return getLongs(parts);
  }

  List<TreeMap<Long, RangeMapping>> parseMaps(Iterator<String> iter) {
    List<TreeMap<Long, RangeMapping>> maps = new ArrayList<>();

    while (iter.hasNext()) {
      String line = iter.next();

      if (line.contains(":")) {
        TreeMap<Long, RangeMapping> map = new TreeMap<>();
        maps.add(map);

        line = iter.next();
        while (!line.isEmpty()) {
          var longs = getLongs(line.split("\\s+"));
          Preconditions.checkArgument(longs.size() == 3);

          long start = longs.get(1);
          long len = longs.get(2);
          long offset = longs.get(0) - start;

          RangeMapping mapping = new RangeMapping(start, len, offset);
          map.put(mapping.start(), mapping);

          try {
            line = iter.next();
          } catch (NoSuchElementException e) {
            break;
          }
        }
      }
    }

    return maps;
  }

  List<Long> getLongs(String[] arr) {
    return Arrays.stream(arr)
        .filter(s -> !s.isBlank())
        .map(Long::parseLong)
        .toList();
  }
}
