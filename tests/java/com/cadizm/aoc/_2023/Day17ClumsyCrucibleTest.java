package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17ClumsyCrucibleTest {

  @Test
  public void testPuzzle1() {
    assertEquals(102, new Day17ClumsyCrucible("day17.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(94, new Day17ClumsyCrucible("day17.input").puzzle2());
  }
}
