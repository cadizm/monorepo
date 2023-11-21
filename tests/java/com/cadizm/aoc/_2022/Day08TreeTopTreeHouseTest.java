package com.cadizm.aoc._2022;

import org.junit.jupiter.api.Test;

import static com.cadizm.aoc._2022.Day08TreeTopTreeHouse.maxScenicScore;
import static com.cadizm.aoc._2022.Day08TreeTopTreeHouse.visibleTrees;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day08TreeTopTreeHouseTest {

  @Test
  public void puzzle1() {
    assertEquals(1782, visibleTrees());
  }

  @Test
  public void puzzle2() {
    assertEquals(474606, maxScenicScore());
  }
}
