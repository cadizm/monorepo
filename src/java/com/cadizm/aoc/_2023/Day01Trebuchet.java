package com.cadizm.aoc._2023;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.cadizm.io.Resource;

// https://adventofcode.com/2023/day/1
public class Day01Trebuchet {

  // The basic idea for this puzzle is to get the first digit and last numeric digits
  // on each line and concatenate them. The puzzle's answer is the sum af these numbers.
  public static long puzzle1() {
    Stream<String> lines = Resource.readLines("day01.input");

    long sum = 0;

    for (var it = lines.iterator(); it.hasNext(); ) {
      String line = it.next();
      for (char c : line.toCharArray()) {
        if (Character.isDigit(c)) {
          // multiply by 10 to move to correct position
          sum += 10L * Character.getNumericValue(c);
          break;
        }
      }

      String reversed = new StringBuilder(line).reverse().toString();
      for (char c : reversed.toCharArray()) {
        if (Character.isDigit(c)) {
          sum += Character.getNumericValue(c);
          break;
        }
      }
    }

    return sum;
  }

  /**
   * Record representing a digit found by "number" or "word".
   *
   * @param val The value of this digit
   * @param index The index the digit was found or -1 if not found
   */
  record Digit(int val, int index) {}

  record Digits(Digit numberDigit, Digit wordDigit) {}

  // The idea here is to look for _both_ "number digits" (i.e. 0-9) as well as
  // "word digits" (i.e. one, two, ..., nine).
  //
  // If only one is found, it is used. If both are found, use the leftmost digit
  // since it comes first and has precedence according to the problem statement.
  //
  // Finding a number/word digit from the end is handled similarly as finding it
  // from the start by reversing the string as well as the word digits in kind.
  public static long puzzle2() {
    Stream<String> lines = Resource.readLines("day01.input");

    long sum = 0;

    for (var it = lines.iterator(); it.hasNext(); ) {
      String line = it.next();
      Digits startDigits = getDigits(line, false);
      sum += 10L * getVal(startDigits);

      Digits endDigits = getDigits(line, true);
      sum += getVal(endDigits);
    }

    return sum;
  }

  /**
   * Look for and return both the "number digit" and "word digit" in s.
   *
   * If reverse is true, reverse the string and look for "reverse word digits".
   */
  static Digits getDigits(String s, boolean reverse) {
    String regex = "^.*?(?<digit>one|two|three|four|five|six|seven|eight|nine).*?$";
    if (reverse) {
      s = new StringBuilder(s).reverse().toString();
      regex = "^.*?(?<digit>eno|owt|eerht|ruof|evif|xis|neves|thgie|enin).*?$";
    }

    Digit numberDigit = new Digit(0, -1);
    for (int i = 0; i < s.length(); ++i) {
      char c = s.charAt(i);
      if (Character.isDigit(c)) {
        numberDigit = new Digit(Character.getNumericValue(c), i);
        break;
      }
    }

    Digit wordDigit = new Digit(0, -1);
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(s);

    if (matcher.matches()) {
      wordDigit = new Digit(getVal(matcher.group("digit")), matcher.start("digit"));
    }

    return new Digits(numberDigit, wordDigit);
  }

  /**
   * Determine the leftmost digit and return its val.
   */
  static int getVal(Digits digits) {
    // Early return if only one digit found
    if (digits.numberDigit().index() == -1) {
      return digits.wordDigit().val();
    } else if (digits.wordDigit().index() == -1) {
      return digits.numberDigit().val();
    }

    // Both number and word digits were found
    Digit number = digits.numberDigit();
    Digit word = digits.wordDigit();

    return number.index() < word.index() ? number.val() : word.val();
  }

  static int getVal(String s) {
    return switch (s) {
      case "one", "eno" -> 1;
      case "two", "owt" -> 2;
      case "three", "eerht" -> 3;
      case "four", "ruof" -> 4;
      case "five", "evif" -> 5;
      case "six", "xis" -> 6;
      case "seven", "neves" -> 7;
      case "eight", "thgie" -> 8;
      case "nine", "enin" -> 9;
      default -> throw new RuntimeException("Couldn't find val for: " + s);
    };
  }
}
