package com.cadizm.string;

import com.google.common.base.Preconditions;

public class Str {

  public static String leftpad(String s, int len) {
    Preconditions.checkArgument(s != null && len > 0);

    return String.format("%1$" + len + "s", s);
  }
}
