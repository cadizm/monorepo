package com.cadizm.math;

import java.util.ArrayList;
import java.util.List;

import com.cadizm.number.Number;

public class Product {

  /**
   * Return the smallest positive integer such that the product of its digits
   * equals n.
   *
   * If no such number exists, return -1.
   */
  public static int digitsProduct(int n) {
    if (n == 0) {
      return 10;
    } else if (n == 1) {
      return 1;
    }

    List<Integer> digits = new ArrayList<>();

    List<Integer> divisors = List.of(9, 8, 7, 6, 5, 4, 3, 2);

    for (var divisor : divisors) {
      while (n % divisor == 0) {
        n /= divisor;
        digits.add(0, divisor);
      }
    }

    if (digits.isEmpty()) {
      return -1;
    }

    return Number.getInt(digits);
  }
}
