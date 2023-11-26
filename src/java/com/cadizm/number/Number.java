package com.cadizm.number;

import java.util.HashSet;
import java.util.List;
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

    for (char c : chars) {
      // increase result by power of 10 and add digit
      res = res * 10 + getInt(c);

      if (res < 0) {
        throw new ArithmeticException("Integer overflow: " + res);
      }
    }

    return res;
  }

  /**
   * Return the base 10 integer value obtained by "concatenating" the list of
   * non-negative integer `digits`.
   *
   * For example, given [1, 0, 2, 4], this method will return 1024.
   */
  public static int getInt(List<Integer> digits) {
    Preconditions.checkArgument(digits != null);

    int res = 0;

    for (var digit : digits) {
      Preconditions.checkArgument(digit >= 0);

      res = res * 10 + digit;

      if (res < 0) {
        throw new ArithmeticException("Integer overflow: " + res);
      }
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
  public static Set<Integer> deleteSingleDigit(int n) {
    Preconditions.checkArgument(n > 0);

    Set<Integer> res = new HashSet<>();

    for (int d = 1; d <= n; d *= 10) {
      int number = (n % d) + (((n / d) / 10) * d);
      res.add(number);
    }

    return res;
  }

  public static int reverseDigits(int n) {
    Preconditions.checkArgument(n >= 0);

    int res = 0;

    while (n > 0) {
      int digit = n % 10;
      res = res * 10 + digit;
      n /= 10;
    }

    return res;
  }
}
