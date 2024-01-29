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
  public static int gcd(int a, int b) {
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

  public static int lcm(int a, int b) {
    Preconditions.checkArgument(a > 0 && b > 0);

    return a * (b / gcd(a, b));
  }

  public static BigInteger lcm(BigInteger a, BigInteger b) {
    Preconditions.checkArgument(a.compareTo(BigInteger.ZERO) > 0);
    Preconditions.checkArgument(b.compareTo(BigInteger.ZERO) > 0);

    return a.multiply(b.divide(a.gcd(b)));
  }

  public static BigInteger lcm(List<Integer> nums) {
    Preconditions.checkArgument(nums.stream().allMatch(n -> n > 0));

    return nums.stream()
        .map(String::valueOf)
        .map(BigInteger::new)
        .reduce(BigInteger.ONE, Maths::lcm);
  }

  /**
   * Return the sum of absolute differences between 2 points on a Cartesian plane.
   */
  public static long manhattanDistance(long x1, long y1, long x2, long y2) {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
  }
}
