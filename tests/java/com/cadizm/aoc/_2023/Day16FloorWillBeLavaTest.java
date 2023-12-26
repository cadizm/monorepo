package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day16FloorWillBeLavaTest {

  @Test
  public void testPuzzle1() {
    assertEquals(46, new Day16FloorWillBeLava("day16.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(51, new Day16FloorWillBeLava("day16.input").puzzle2());
  }
}
