package com.cadizm.aoc._2022;

import org.junit.jupiter.api.Test;

import static com.cadizm.aoc._2022.Day03RucksackReorganization.prioritySum;
import static com.cadizm.aoc._2022.Day03RucksackReorganization.prioritySum3Elves;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day03RucksackReorganizationTest {

  @Test
  public void puzzle1() {
    assertEquals(7691, prioritySum());
  }

  @Test
  public void puzzle2() {
    assertEquals(2508, prioritySum3Elves());
  }
}
