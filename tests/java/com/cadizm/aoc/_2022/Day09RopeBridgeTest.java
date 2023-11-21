package com.cadizm.aoc._2022;

import org.junit.jupiter.api.Test;

import static com.cadizm.aoc._2022.Day09RopeBridge.tenKnotRopeVisitedTailPositions;
import static com.cadizm.aoc._2022.Day09RopeBridge.twoKnotRopeVisitedTailPositions;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day09RopeBridgeTest {

  @Test
  public void puzzle1() {
    assertEquals(5907, twoKnotRopeVisitedTailPositions());
  }

  @Test
  public void puzzle2() {
    assertEquals(2303, tenKnotRopeVisitedTailPositions());
  }
}
