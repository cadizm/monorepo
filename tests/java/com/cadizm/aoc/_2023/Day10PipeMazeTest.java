package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10PipeMazeTest {

  @Test
  public void testPuzzle1() {
    assertEquals(4, new Day10PipeMaze("day10-1a.input").puzzle1());
    assertEquals(8, new Day10PipeMaze("day10-1b.input").puzzle1());
    //assertEquals(6613, new Day10PipeMaze("secret/day10.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
    //System.out.println(new Day10PipeMaze("day10.input").puzzle2());
  }
}
