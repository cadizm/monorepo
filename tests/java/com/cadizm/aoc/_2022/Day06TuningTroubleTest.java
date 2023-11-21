package com.cadizm.aoc._2022;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static com.cadizm.aoc._2022.Day06TuningTrouble.startOfMessage;
import static com.cadizm.aoc._2022.Day06TuningTrouble.startOfPacket;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day06TuningTroubleTest {

  @Test
  public void puzzle1() throws IOException {
    assertEquals(1647, startOfPacket());
  }

  @Test
  public void puzzle2() throws IOException {
    assertEquals(2447, startOfMessage());
  }
}
