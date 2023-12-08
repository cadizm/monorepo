package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02CubeConundrumTest {

  @Test
  public void testPuzzle1() {
    assertEquals(8, new Day02CubeConundrum().puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(2286, new Day02CubeConundrum().puzzle2());
  }
}
