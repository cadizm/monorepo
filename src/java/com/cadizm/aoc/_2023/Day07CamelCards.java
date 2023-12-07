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
    this.hands = parseInput();
  }

  record Card(char label, int value) implements Comparable<Card> {
    @Override
    public int compareTo(Card other) {
      return this.value - other.value;
    }
  }

  // Array of cards of length 5
  record Hand(List<Card> cards, int bid, HandType type) implements Comparable<Hand> {
    @Override
    public int compareTo(Hand other) {
      if (type == other.type) {
        for (int i = 0; i < 5; ++i) {
          Card a = cards.get(i);
          Card b = other.cards.get(i);
          if (a.label != b.label) {
            return a.compareTo(b);
          }
        }
        return 0;
      }
      return this.type().compareTo(other.type());
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

  public int puzzle1() {
    List<Hand> sorted = new ArrayList<>(hands);
    Collections.sort(sorted);

    int sum = 0;

    for (int i = 0; i < sorted.size(); ++i) {
      sum += sorted.get(i).bid() * (i + 1);
    }

    return sum;
  }

  public int puzzle2() {
    return 0;
  }

  int cardValue(char label) {
    if (Character.isDigit(label)) {
      return Character.getNumericValue(label);
    }

    return switch (label) {
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
      case 5 -> HandType.HIGH_CARD;
      case 4 -> HandType.ONE_PAIR;
      case 3 -> map.containsValue(3) ? HandType.THREE_OF_KIND : HandType.TWO_PAIR;
      case 2 -> map.containsValue(4) ? HandType.FOUR_OF_A_KIND : HandType.FULL_HOUSE;
      case 1 -> HandType.FIVE_OF_A_KIND;
      default -> throw new RuntimeException("Unhandled map size: " + map.size());
    };
  }

  List<Hand> parseInput() {
    Stream<String> stream = Resource.readLines("day07.input");

    List<Hand> hands = new ArrayList<>();

    for (var iter = stream.iterator(); iter.hasNext(); ) {
      String line = iter.next();
      String[] parts = line.split(" ");

      List<Card> cards = parseCards(parts[0]);
      int bid = Integer.parseInt(parts[1]);
      HandType type = handType(cards);

      hands.add(new Hand(cards, bid, type));
    }

    return hands;
  }

  List<Card> parseCards(String line) {
    String[] labels = line.split("");

    return Arrays.stream(labels)
        .map(s -> s.charAt(0))
        .map(c -> new Card(c, cardValue(c)))
        .toList();
  }
}
