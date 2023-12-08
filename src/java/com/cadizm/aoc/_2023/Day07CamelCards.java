package com.cadizm.aoc._2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.cadizm.io.Resource;
import com.google.common.base.Preconditions;

// https://adventofcode.com/2023/day/7
public class Day07CamelCards {

  private final List<Hand> hands;

  public Day07CamelCards() {
    this(false);
  }

  public Day07CamelCards(boolean useJokers) {
    this.hands = parseInput(useJokers);
  }

  record Card(char label, int value) implements Comparable<Card> {
    @Override
    public int compareTo(Card other) {
      return this.value - other.value;
    }
  }

  record Hand(List<Card> cards, int bid, HandType type) implements Comparable<Hand> {
    @Override
    public int compareTo(Hand other) {
      if (type != other.type) {
        return this.type().compareTo(other.type());
      }
      for (int i = 0; i < 5; ++i) {
        Card a = cards.get(i);
        Card b = other.cards.get(i);
        if (a.label != b.label) {
          return a.compareTo(b);
        }
      }
      return 0;
    }
  }

  enum HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_KIND,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    FIVE_OF_A_KIND,
  }

  // Nothing fancy here, just a straightforward implementation of the
  // problem description using Comparable interface in order to sort.
  public int puzzle1() {
    List<Hand> sorted = new ArrayList<>(hands);
    Collections.sort(sorted);

    int sum = 0;

    for (int i = 0; i < sorted.size(); ++i) {
      sum += sorted.get(i).bid() * (i + 1);
    }

    return sum;
  }

  // Use same scoring as part 1, the logic to determine hand type using
  // Jokers is done during construction. Instead of trying all possible
  // hand types, just determine the best possible hand that can be made
  // with the given hand and number of Jokers.
  public int puzzle2() {
    return puzzle1();
  }

  int cardValue(char label) {
    if (Character.isDigit(label)) {
      return Character.getNumericValue(label);
    }

    return switch (label) {
      case '?' -> 1;  // Jokers are weakest
      case 'T' -> 10;
      case 'J' -> 11;
      case 'Q' -> 12;
      case 'K' -> 13;
      case 'A' -> 14;
      default -> throw new RuntimeException("Unhandled label: " + label);
    };
  }

  HandType handType(List<Card> cards) {
    Preconditions.checkArgument(cards.size() == 5);

    Map<Card, Integer> map = new HashMap<>();

    for (var card : cards) {
      map.put(card, map.getOrDefault(card, 0) + 1);
    }

    return switch (map.size()) {
      case 5 -> HandType.HIGH_CARD;         // 1, 2, 3, 4, 5
      case 4 -> HandType.ONE_PAIR;          // 1, 1, 2, 3, 4
      case 3 -> map.containsValue(3) ?
          HandType.THREE_OF_KIND :          // 1, 1, 1, 2, 3
          HandType.TWO_PAIR;                // 1, 1, 2, 2, 3
      case 2 -> map.containsValue(4) ?
          HandType.FOUR_OF_A_KIND :         // 1, 1, 1, 1, 2
          HandType.FULL_HOUSE;              // 1, 1, 1, 2, 2
      case 1 -> HandType.FIVE_OF_A_KIND;    // 1, 1, 1, 1, 1
      default -> throw new RuntimeException("");
    };
  }

  HandType handTypeUsingJokers(List<Card> cards) {
    Preconditions.checkArgument(cards.size() == 5);

    boolean noJokers = cards.stream()
        .filter(card -> card.label == '?')
        .findAny()
        .isEmpty();

    if (noJokers) {
      return handType(cards);
    }

    Map<Character, Integer> map = new HashMap<>();

    for (var card : cards) {
      map.put(card.label, map.getOrDefault(card.label, 0) + 1);
    }

    int jokerCount = map.remove('?');  // take out jokers

    return switch (jokerCount) {
      case 1 -> switch (map.size()) {          // 1 Joker
        case 1 -> HandType.FIVE_OF_A_KIND;     // 1, 1, 1, 1
        case 2 -> map.containsValue(3) ?
            HandType.FOUR_OF_A_KIND :          // 1, 1, 1, 2
            HandType.FULL_HOUSE;               // 1, 1, 2, 2
        case 3 -> HandType.THREE_OF_KIND;      // 1, 1, 2, 3
        case 4 -> HandType.ONE_PAIR;           // 1, 2, 3, 4
        default -> throw new RuntimeException();
      };
      case 2 -> switch (map.size()) {          // 2 Jokers
        case 1 -> HandType.FIVE_OF_A_KIND;     // 1, 1, 1
        case 2 -> HandType.FOUR_OF_A_KIND;     // 1, 1, 2
        case 3 -> HandType.THREE_OF_KIND;      // 1, 2, 3
        default -> throw new RuntimeException();
      };
      case 3 -> map.containsValue(2) ?         // 3 Jokers
          HandType.FIVE_OF_A_KIND :            // 1, 1
          HandType.FOUR_OF_A_KIND;             // 1, 2
      case 4, 5 -> HandType.FIVE_OF_A_KIND;    // 4, 5 Jokers
      default -> throw new RuntimeException();
    };
  }

  List<Hand> parseInput(boolean useJokers) {
    Stream<String> stream = Resource.readLines("day07.input");

    List<Hand> hands = new ArrayList<>();

    for (var iter = stream.iterator(); iter.hasNext(); ) {
      String line = iter.next();
      String[] parts = line.split(" ");

      List<Card> cards = parseCards(parts[0], useJokers);
      int bid = Integer.parseInt(parts[1]);
      HandType type = useJokers ? handTypeUsingJokers(cards) : handType(cards);

      hands.add(new Hand(cards, bid, type));
    }

    return hands;
  }

  List<Card> parseCards(String line, boolean useJokers) {
    String[] labels = line.split("");

    return Arrays.stream(labels)
        .map(s -> s.charAt(0))
        .map(c -> useJokers && c == 'J' ? '?' : c)
        .map(c -> new Card(c, cardValue(c)))
        .toList();
  }
}
