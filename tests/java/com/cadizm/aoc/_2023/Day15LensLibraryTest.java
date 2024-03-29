package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15LensLibraryTest {

  @Test
  public void testPuzzle1() {
    assertEquals(1320, new Day15LensLibrary("day15.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(145, new Day15LensLibrary("day15.input").puzzle2());
  }
}
