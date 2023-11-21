package com.cadizm.aoc._2022;

import org.junit.jupiter.api.Test;

import static com.cadizm.aoc._2022.Day14RegolithReservoir.restingSandUnits;
import static com.cadizm.aoc._2022.Day14RegolithReservoir.restingSandUnitsWithFloor;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14RegolithReservoirTest {

  @Test
  public void puzzle1() {
    assertEquals(897, restingSandUnits());
  }

  @Test
  public void puzzle2() {
    assertEquals(26683, restingSandUnitsWithFloor());
  }
}
