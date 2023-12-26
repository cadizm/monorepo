package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07CamelCardsTest {

  @Test
  public void testPuzzle1() {
    assertEquals(6440, new Day07CamelCards().puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(5905, new Day07CamelCards(true).puzzle2());
  }
}
