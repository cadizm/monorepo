package com.cadizm.number;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IntTest {

  @Test
  public void testGetIntInvalidChar() {
    assertThrows(IllegalArgumentException.class, () -> Int.getInt('a') );
  }

  @Test
  public void testGetIntSingleChar() {
    assertEquals(0, Int.getInt('0'));
    assertEquals(1, Int.getInt('1'));
    assertEquals(2, Int.getInt('2'));
    assertEquals(3, Int.getInt('3'));
    assertEquals(4, Int.getInt('4'));
    assertEquals(5, Int.getInt('5'));
    assertEquals(6, Int.getInt('6'));
    assertEquals(7, Int.getInt('7'));
    assertEquals(8, Int.getInt('8'));
    assertEquals(9, Int.getInt('9'));
  }

  @Test
  void testGetIntFromString() {
    assertEquals(0, Int.getInt("0"));
    assertEquals(1, Int.getInt("1"));
    assertEquals(10, Int.getInt("10"));
    assertEquals(1, Int.getInt("01"));
    assertEquals(1234567890, Int.getInt("1234567890"));
  }

  @Test
  void testGetIntResultTooLarge() {
    assertEquals(Integer.parseInt("2147483647"), Integer.MAX_VALUE);
    assertThrows(ArithmeticException.class, () -> Int.getInt("2147483648") );
  }
}
