package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11CosmicExpansionTest {

  @Test
  public void testPuzzle1() {
    assertEquals(374, new Day11CosmicExpansion("day11.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(1030, new Day11CosmicExpansion("day11.input").puzzle2(10));
    assertEquals(8410, new Day11CosmicExpansion("day11.input").puzzle2(100));
  }
}
