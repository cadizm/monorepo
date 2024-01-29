package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10PipeMazeTest {

  @Test
  public void testPuzzle1() {
    assertEquals(4, new Day10PipeMaze("day10-1a.input").puzzle1());
    assertEquals(8, new Day10PipeMaze("day10-1b.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(4, new Day10PipeMaze("day10-2a.input").puzzle2());
    assertEquals(4, new Day10PipeMaze("day10-2b.input").puzzle2());
    assertEquals(8, new Day10PipeMaze("day10-2c.input").puzzle2());
    assertEquals(10, new Day10PipeMaze("day10-2d.input").puzzle2());
  }
}
