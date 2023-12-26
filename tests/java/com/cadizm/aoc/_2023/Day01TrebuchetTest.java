package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01TrebuchetTest {

  @Test
  public void testPuzzle1() {
    assertEquals(209, Day01Trebuchet.puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(281, Day01Trebuchet.puzzle2());
  }
}
