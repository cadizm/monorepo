package com.cadizm.math;

import java.math.BigInteger;
import java.util.List;

import com.google.common.base.Preconditions;

public class Maths {

  /**
   * Return the greatest common divisor of a and b.
   *
   * GCD (also known as "highest common factor") is calculated using
   * Euclid's algorithm: https://en.wikipedia.org/wiki/Euclidean_algorithm
   */
  static int gcd(int a, int b) {
    Preconditions.checkArgument(a > 0 && b > 0);

    if (a < b) {
      return gcd(b, a);
    }

    int remainder;
    while ((remainder = a % b) != 0) {
      a = b;
      b = remainder;
    }

    return b;
  }

  static int lcm(int a, int b) {
    Preconditions.checkArgument(a > 0 && b > 0);

    return a * (b / gcd(a, b));
  }

  static BigInteger lcm(BigInteger a, BigInteger b) {
    Preconditions.checkArgument(a.compareTo(BigInteger.ZERO) > 0);
    Preconditions.checkArgument(b.compareTo(BigInteger.ZERO) > 0);

    return a.multiply(b.divide(a.gcd(b)));
  }

  static BigInteger lcm(List<Integer> nums) {
    Preconditions.checkArgument(nums.stream().allMatch(n -> n > 0));

    return nums.stream()
        .map(String::valueOf)
        .map(BigInteger::new)
        .reduce(BigInteger.ONE, Maths::lcm);
  }
}
