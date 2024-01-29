package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03GearRatiosTest {

  @Test
  public void testPuzzle1() {
    assertEquals(4361, new Day03GearRatios().puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(467835, new Day03GearRatios().puzzle2());
  }
}
