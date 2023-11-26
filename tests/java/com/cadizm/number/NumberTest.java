package com.cadizm.number;

import java.util.List;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NumberTest {

  @Test
  public void testGetIntInvalidChar() {
    assertThrows(IllegalArgumentException.class, () -> Number.getInt('a'));
  }

  @Test
  public void testGetIntSingleChar() {
    assertEquals(0, Number.getInt('0'));
    assertEquals(1, Number.getInt('1'));
    assertEquals(2, Number.getInt('2'));
    assertEquals(3, Number.getInt('3'));
    assertEquals(4, Number.getInt('4'));
    assertEquals(5, Number.getInt('5'));
    assertEquals(6, Number.getInt('6'));
    assertEquals(7, Number.getInt('7'));
    assertEquals(8, Number.getInt('8'));
    assertEquals(9, Number.getInt('9'));
  }

  @Test
  void testGetIntFromString() {
    assertEquals(0, Number.getInt("0"));
    assertEquals(1, Number.getInt("1"));
    assertEquals(10, Number.getInt("10"));
    assertEquals(1, Number.getInt("01"));
    assertEquals(1234567890, Number.getInt("1234567890"));
  }

  @Test
  void testGetIntResultTooLarge() {
    assertEquals(Integer.parseInt("2147483647"), Integer.MAX_VALUE);
    assertThrows(ArithmeticException.class, () -> Number.getInt("2147483648"));
  }

  @Test
  public void testDeleteSingleDigitNegative() {
    assertThrows(IllegalArgumentException.class, () -> Number.deleteSingleDigit(-1));
    assertThrows(IllegalArgumentException.class, () -> Number.deleteSingleDigit(-17));
  }

  @Test
  public void testDeleteSingleDigitZero() {
    assertThrows(IllegalArgumentException.class, () -> Number.deleteSingleDigit(0));
  }

  @Test
  public void testDeleteSingleDigitOneThruNine() {
    for (int i = 1; i < 10; ++i) {
      var numbers = Number.deleteSingleDigit(i);
      assertThat(numbers, contains(0));
    }
  }

  @Test
  public void testDeleteSingleDigitGreaterTen() {
    var numbers = Number.deleteSingleDigit(10);
    assertThat(numbers, containsInAnyOrder(1, 0));

    numbers = Number.deleteSingleDigit(10000);
    assertThat(numbers, containsInAnyOrder(0, 1000));

    numbers = Number.deleteSingleDigit(101010);
    assertThat(numbers, containsInAnyOrder(1010, 11010, 10010, 10110, 10100, 10101));

    numbers = Number.deleteSingleDigit(101011);
    assertThat(numbers, containsInAnyOrder(1011, 11011, 10011, 10111, 10101));

    numbers = Number.deleteSingleDigit(123456);
    assertThat(numbers, containsInAnyOrder(23456, 13456, 12456, 12356, 12346, 12345));
  }

  @Test
  public void testGetIntFromListDigits() {
    assertEquals(0, Number.getInt(List.of(0)));
    assertEquals(1, Number.getInt(List.of(1)));
    assertEquals(1204, Number.getInt(List.of(1, 2, 0, 4)));
    assertEquals(123, Number.getInt(List.of(0, 0, 1, 2, 3)));
    assertEquals(831052501, Number.getInt(List.of(8, 3, 1, 0, 5, 2, 5, 0, 1)));

    assertThrows(ArithmeticException.class,
        () -> Number.getInt(List.of(2, 1, 4, 7, 4, 8, 3, 6, 4, 8)));
  }

  @Test
  public void testReverseDigits() {
    assertEquals(0, Number.reverseDigits(0));
    assertEquals(1, Number.reverseDigits(10));
    assertEquals(2, Number.reverseDigits(200));
    assertEquals(222, Number.reverseDigits(222));
    assertEquals(987654321, Number.reverseDigits(1234567890));
    assertEquals(1345678902, Number.reverseDigits(2098765431));
  }
}
