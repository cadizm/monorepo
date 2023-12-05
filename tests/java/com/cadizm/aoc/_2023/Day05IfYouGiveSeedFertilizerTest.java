package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day05IfYouGiveSeedFertilizerTest {

  @Test
  public void testPuzzle1() {
    assertEquals(35, new Day05IfYouGiveSeedFertilizer().puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(46, new Day05IfYouGiveSeedFertilizer().puzzle2());
  }
}
