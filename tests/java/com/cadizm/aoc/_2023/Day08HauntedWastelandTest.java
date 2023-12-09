package com.cadizm.aoc._2023;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08HauntedWastelandTest {

  @Test
  public void testPuzzle1() {
    assertEquals(6, new Day08HauntedWasteland("day08-1.input").puzzle1());
  }

  @Test
  public void testPuzzle2() {
    assertEquals(new BigInteger("6"), new Day08HauntedWasteland("day08-2.input").puzzle2());
  }
}
