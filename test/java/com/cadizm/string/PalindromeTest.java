package com.cadizm.string;

import org.junit.jupiter.api.Test;

import static com.cadizm.string.Palindrome.isPalindrome;
import static com.cadizm.string.Palindrome.makePalindrome;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PalindromeTest {

  @Test
  public void testIsPalindrome() {
    assertTrue(isPalindrome(""));
    assertTrue(isPalindrome("a"));
    assertTrue(isPalindrome("aa"));
    assertTrue(isPalindrome("aba"));
    assertTrue(isPalindrome("abba"));
    assertTrue(isPalindrome("abcdcba"));

    assertFalse(isPalindrome("ab"));
    assertFalse(isPalindrome("abc"));
    assertFalse(isPalindrome("abca"));
    assertFalse(isPalindrome("abcb"));
  }

  @Test
  public void testMakePalindrome() {
    assertEquals("", makePalindrome(""));
    assertEquals("a", makePalindrome("a"));
    assertEquals("aba", makePalindrome("ab"));
    assertEquals("abba", makePalindrome("abb"));
    assertEquals("abccba", makePalindrome("abccb"));
    assertEquals("abcdcba", makePalindrome("abcdc"));
    assertEquals("abba", makePalindrome("abba"));
  }
}
