package com.cadizm.number;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;

public class DigitTest {

  @Test
  public void testDeleteSingleDigitNegative() {
    var numbers = Digit.deleteSingleDigit(-1);
    assertThat(numbers, hasSize(0));

    numbers = Digit.deleteSingleDigit(-17);
    assertThat(numbers, hasSize(0));
  }

  @Test
  public void testDeleteSingleDigitZero() {
    var numbers = Digit.deleteSingleDigit(0);
    assertThat(numbers, hasSize(0));
  }

  @Test
  public void testDeleteSingleDigitOneThruNine() {
    for (int i = 1; i < 10; ++i) {
      var numbers = Digit.deleteSingleDigit(i);
      assertThat(numbers, contains(0));
    }
  }

  @Test
  public void testDeleteSingleDigitGreaterTen() {
    var numbers = Digit.deleteSingleDigit(10);
    assertThat(numbers, containsInAnyOrder(1, 0));

    numbers = Digit.deleteSingleDigit(10000);
    assertThat(numbers, containsInAnyOrder(0, 1000));

    numbers = Digit.deleteSingleDigit(101010);
    assertThat(numbers, containsInAnyOrder(1010, 11010, 10010, 10110, 10100, 10101));

    numbers = Digit.deleteSingleDigit(101011);
    assertThat(numbers, containsInAnyOrder(1011, 11011, 10011, 10111, 10101));

    numbers = Digit.deleteSingleDigit(123456);
    assertThat(numbers, containsInAnyOrder(23456, 13456, 12456, 12356, 12346, 12345));
  }
}
