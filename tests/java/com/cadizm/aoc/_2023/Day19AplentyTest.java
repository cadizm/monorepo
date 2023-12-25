package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day19AplentyTest {

  @Test
  public void testPuzzle1() {
    assertEquals(19114, new Day19Aplenty("day19.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(167409079868000L, new Day19Aplenty("day19.input").puzzle2());
  }
}
