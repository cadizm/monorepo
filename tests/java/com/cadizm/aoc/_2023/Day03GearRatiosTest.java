package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03GearRatiosTest {

  @Test
  public void testPuzzle1() {
    assertEquals(528819, new Day03GearRatios().puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(80403602, new Day03GearRatios().puzzle2());
  }
}
