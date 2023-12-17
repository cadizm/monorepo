package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13PointOfIncidenceTest {

  @Test
  public void testPuzzle1() {
    assertEquals(405, new Day13PointOfIncidence("day13.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(400, new Day13PointOfIncidence("day13.input").puzzle2());
  }
}
