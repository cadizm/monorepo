package com.cadizm.aoc._2022;

import org.junit.jupiter.api.Test;

import static com.cadizm.aoc._2022.Day10CathodeRayTube.racingTheBeam;
import static com.cadizm.aoc._2022.Day10CathodeRayTube.signalStrength;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day10CathodeRayTubeTest {

  @Test
  public void puzzle1() {
    assertEquals(14620, signalStrength());
  }

  @Test
  public void puzzle2() {
    String expected =
        "###.. ..##. ####. ###.. #..#. ###.. ####. #..#. \n" +
        "#..#. ...#. #.... #..#. #..#. #..#. #.... #..#. \n" +
        "###.. ...#. ###.. #..#. ####. #..#. ###.. #..#. \n" +
        "#..#. ...#. #.... ###.. #..#. ###.. #.... #..#. \n" +
        "#..#. #..#. #.... #.#.. #..#. #.#.. #.... #..#. \n" +
        "###.. .##.. #.... #..#. #..#. #..#. #.... .##.. \n";

    assertEquals(expected, racingTheBeam());
  }
}
