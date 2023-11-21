package com.cadizm.aoc._2022;

import org.junit.jupiter.api.Test;

import static com.cadizm.aoc._2022.Day12HillClimbingAlgorithm.fewestStepsFromAnyStart;
import static com.cadizm.aoc._2022.Day12HillClimbingAlgorithm.fewestStepsFromGivenStart;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12HillClimbingAlgorithmTest {

  @Test
  public void puzzle1() {
    assertEquals(380, fewestStepsFromGivenStart());
  }

  @Test
  public void puzzle2() {
    assertEquals(375, fewestStepsFromAnyStart());
  }
}
