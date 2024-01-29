package com.cadizm.aoc._2023;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06WaitForItTest {

  @Test
  public void testPuzzle1() {
    assertEquals(288, new Day06WaitForIt().puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(new BigInteger("71503"), new Day06WaitForIt().puzzle2());
  }
}
