package com.cadizm.number;

import java.util.HashSet;
import java.util.Set;

import com.google.common.base.Preconditions;

public class Number {

  /**
   * Return the integer value for char c.
   */
  public static int getInt(char c) {
    Preconditions.checkArgument(c >= '0' && c <= '9');

    return c - '0';
  }

  /**
   * Return the integer value of the concatenated char array.
   *
   * @throws java.util.IllegalFormatException on bad input
   * @throws ArithmeticException on integer overflow
   */
  public static int getInt(char[] chars) {
    Preconditions.checkArgument(chars != null);

    int res = 0;

    for (int i = 0; i < chars.length; ++i) {
      double temp = Math.pow(10, i) * getInt(chars[chars.length - i - 1]);

      if (res + temp > Integer.MAX_VALUE) {
        throw new ArithmeticException(String.format("Result too large: %s", res + temp));
      }

      res += temp;
    }

    return res;
  }

  /**
   * Return the integer value of String s.
   */
  public static int getInt(String s) {
    Preconditions.checkArgument(s != null);

    return getInt(s.toCharArray());
  }

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
