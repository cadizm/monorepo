package com.cadizm.aoc._2022;

import org.junit.jupiter.api.Test;

import static com.cadizm.aoc._2022.Day11MonkeyInTheMiddle.monkeyBusinessLevel;
import static com.cadizm.aoc._2022.Day11MonkeyInTheMiddle.monkeyBusinessLevelAlternateRelief;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day11MonkeyInTheMiddleTest {

  @Test
  public void puzzle1() {
    assertEquals(112815L, monkeyBusinessLevel());
  }

  @Test
  public void puzzle2() {
    assertEquals(25738411485L, monkeyBusinessLevelAlternateRelief());
  }

}
