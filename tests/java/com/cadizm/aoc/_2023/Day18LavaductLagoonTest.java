package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day18LavaductLagoonTest {

  @Test
  public void testPuzzle1() {
    assertEquals(62, new Day18LavaductLagoon("day18.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(952408144115L, new Day18LavaductLagoon("day18.input").puzzle2());
  }
}
