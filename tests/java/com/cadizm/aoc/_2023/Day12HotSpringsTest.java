package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12HotSpringsTest {

  @Test
  public void testPuzzle1() {
    assertEquals(21, new Day12HotSprings("day12.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(525152, new Day12HotSprings("day12.input", 5).puzzle2());
  }
}
