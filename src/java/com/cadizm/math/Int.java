package com.cadizm.math;

import com.google.common.base.Preconditions;

public class Int {

  public static int getInt(char c) {
    Preconditions.checkArgument(c >= '0' && c <= '9');

    return c - '0';
  }

  /**
   * Return the integer value of the char array.
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

  public static int getInt(String s) {
    Preconditions.checkArgument(s != null);

    return getInt(s.toCharArray());
  }
}
