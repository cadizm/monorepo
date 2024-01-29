package com.cadizm.aoc._2023;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
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

  // Process input similarly as part 1
  // Create sorted map: card -> number of matches (map 1)
  // Create another map: card -> number of copies, initial val 1. (map 2)
  // Traverse map 1 in sorted order
  // Update map 2 keys using current key number of matches
  // Return the total number of all copies of cards
  public int puzzle2() {
    Stream<String> stream = Resource.readLines("day04.input");

    SortedMap<Integer, Integer> cardMatches = new TreeMap<>();

    // First pass, collect number of matches for each card
    for (var iter = stream.iterator(); iter.hasNext(); ) {
      String line = iter.next();

      String[] parts = line.split(": ");
      int card = Integer.parseInt(parts[0].split("\\s+")[1]);

      parts = parts[1].split(" \\| ");
      List<Integer> winning = parseNumbers(parts[0]);
      List<Integer> have = parseNumbers(parts[1]);

      int n = intersection(winning, have).size();
      cardMatches.put(card, n);
    }

    // Initialize map with number of copies of each card, initially 1
    Map<Integer, Integer> cardCopies = new HashMap<>();
    for (int card : cardMatches.keySet()) {
      cardCopies.put(card, 1);
    }

    // Update cardCopies based on number of matches for each entry
    // Outer loop iterates over each card number. Traversal of cards in
    // ascending order is guaranteed because cardMatches is a SortedMap
    for (int card : cardMatches.keySet()) {
      // Current tally of this card's number of copies
      int copies = cardCopies.get(card);

      // For each copy of this card, update the number of copies of the
      // cards below based on the number of matches this card has
      for (int i = 0; i < copies; ++i) {
        int matches = cardMatches.get(card);

        // Increment copy count of each card covered by matches
        for (int below = card + 1; below <= card + matches; ++below) {
          cardCopies.put(below, cardCopies.get(below) + 1);
        }
      }
    }

    return cardCopies.values()
        .stream()
        .reduce(0, Integer::sum);
  }
}
