package com.cadizm.aoc._2022;

import org.junit.jupiter.api.Test;

import static com.cadizm.aoc._2022.Day07NoSpaceLeftOnDevice.dirsUnder100k;
import static com.cadizm.aoc._2022.Day07NoSpaceLeftOnDevice.findDirToFreeSpace;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day07NoSpaceLeftOnDeviceTest {

  @Test
  public void puzzle1() {
    assertEquals(1792222L, dirsUnder100k());
  }

  @Test
  public void puzzle2() {
    assertEquals(1112963, findDirToFreeSpace());
  }
}
