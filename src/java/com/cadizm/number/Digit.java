package com.cadizm.number;

import java.util.HashSet;
import java.util.Set;

public class Digit {

  /**
   * Return the set of numbers resulting from the deletion of a single digit
   * from positive number n (i.e. greater than 0).
   */
  static Set<Integer> deleteSingleDigit(int n) {

    Set<Integer> res = new HashSet<>();

    for (int d = 1; d <= n; d *= 10) {
      int number = (n % d) + (((n / d) / 10) * d);
      res.add(number);
    }

    return res;
  }
}
