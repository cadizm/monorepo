package com.cadizm.number;

import com.google.common.base.Preconditions;

public class BinaryNumber {

  /**
   * Return the base 10 number from array of binary digits `chars`.
   */
  public static int parseBinary(char[] chars) {
    Preconditions.checkArgument(chars != null);

    int res = 0;

    for (char c : chars) {
      Preconditions.checkArgument(c == '0' || c == '1');
      res = res * 2 + (c - '0');
    }

    return res;
  }
}
