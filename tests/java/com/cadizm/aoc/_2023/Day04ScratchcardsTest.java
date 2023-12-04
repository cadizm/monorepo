package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04ScratchcardsTest {

  @Test
  public void testPuzzle1() {
    assertEquals(21138, new Day04Scratchcards().puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(7185540, new Day04Scratchcards().puzzle2());
  }
}
