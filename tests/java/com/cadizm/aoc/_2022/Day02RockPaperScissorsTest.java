package com.cadizm.aoc._2022;

import org.junit.jupiter.api.Test;

import static com.cadizm.aoc._2022.Day02RockPaperScissors.elfStrategyGuideTotalScore;
import static com.cadizm.aoc._2022.Day02RockPaperScissors.guessedStrategyGuideTotalScore;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day02RockPaperScissorsTest {

  @Test
  public void puzzle1() {
    assertEquals(12156L, guessedStrategyGuideTotalScore());
  }

  @Test
  public void puzzle2() {
    assertEquals(10835L, elfStrategyGuideTotalScore());
  }
}
