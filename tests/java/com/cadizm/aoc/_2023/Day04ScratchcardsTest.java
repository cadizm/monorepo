package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04ScratchcardsTest {

  @Test
  public void testPuzzle1() {
    assertEquals(13, new Day04Scratchcards().puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(30, new Day04Scratchcards().puzzle2());
  }
}
