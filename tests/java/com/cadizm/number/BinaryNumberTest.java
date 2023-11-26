package com.cadizm.number;

import org.junit.jupiter.api.Test;

import static com.cadizm.number.BinaryNumber.parseBinary;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinaryNumberTest {

  @Test
  public void testParseBinary() {
    assertEquals(0, parseBinary("".toCharArray()));
    assertEquals(0, parseBinary("0".toCharArray()));
    assertEquals(1, parseBinary("1".toCharArray()));
    assertEquals(1, parseBinary("01".toCharArray()));
    assertEquals(2, parseBinary("10".toCharArray()));
    assertEquals(3, parseBinary("11".toCharArray()));
    assertEquals(4, parseBinary("100".toCharArray()));
    assertEquals(72, parseBinary("01001000".toCharArray()));
  }
}
