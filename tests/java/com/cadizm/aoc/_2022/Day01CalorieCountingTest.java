package com.cadizm.aoc._2022;

import org.junit.jupiter.api.Test;

import static com.cadizm.aoc._2022.Day01CalorieCounting.maxElfCalories;
import static com.cadizm.aoc._2022.Day01CalorieCounting.sumTop3ElfCalories;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day01CalorieCountingTest {

  @Test
  public void puzzle1() {
    assertEquals(71300L, maxElfCalories());
  }

  @Test
  public void puzzle2() {
    assertEquals(209691L, sumTop3ElfCalories());
  }
}
