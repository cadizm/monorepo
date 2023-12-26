package com.cadizm.aoc._2023;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09MirageMaintenanceTest {

  @Test
  public void testPuzzle1() {
    assertEquals(114, new Day09MirageMaintenance("day09.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(2, new Day09MirageMaintenance("day09.input").puzzle2());
  }
}
