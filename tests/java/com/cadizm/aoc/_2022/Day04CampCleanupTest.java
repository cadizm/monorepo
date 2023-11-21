package com.cadizm.aoc._2022;

import org.junit.jupiter.api.Test;

import static com.cadizm.aoc._2022.Day04CampCleanup.fullyContainedPairs;
import static com.cadizm.aoc._2022.Day04CampCleanup.overlappingPairs;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day04CampCleanupTest {

  @Test
  public void puzzle1() {
    assertEquals(477, fullyContainedPairs());
  }

  @Test
  public void puzzle2() {
    assertEquals(830, overlappingPairs());
  }
}
