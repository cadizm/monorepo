package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14ParabolicReflectorDishTest {

  @Test
  public void testPuzzle1() {
    assertEquals(136, new Day14ParabolicReflectorDish("day14.input").puzzle1());
    assertEquals(108792, new Day14ParabolicReflectorDish("secret/day14.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
  }
}
