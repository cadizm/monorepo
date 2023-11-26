package com.cadizm.string;

import com.google.common.base.Preconditions;

public class Palindrome {

  /**
   * Return the shortest palindrome that can be made by appending to s.
   *
   * Clever alternative implementation:
   *
   * <code>
   * String makePalindrome(String s) {
   *
   *   StringBuilder sb = new StringBuilder(s);
   *   StringCharacterIterator it = new StringCharacterIterator(s);
   *
   *   while (!isPalindrome(sb.toString())) {
   *     sb.insert(s.length(), it.current());
   *     it.next();
   *   }
   *
   *   return sb.toString();
   * }
   * </code>
   */
  public static String makePalindrome(String s) {
    Preconditions.checkArgument(s != null);

    // Find existing palindrome in string: Note that a single character is
    // considered a palindrome, so for non-empty strings, we are guaranteed
    // to find a palindrome
    for (int i = 0; i < s.length(); ++i) {
      char cur = s.charAt(i);  // character at current index
      char end = s.charAt(s.length() - 1);  // fixed at last character

      if (cur == end && isPalindrome(s.substring(i))) {
        // we need to add the reverse of everything from the beginning
        // of the string to the current index to create a palindrome
        return s + reverse(s.substring(0, i));
      }
    }

    return "";
  }

  /**
   * Return true if `s` is a palindrome, false otherwise.
   */
  public static boolean isPalindrome(String s) {
    Preconditions.checkArgument(s != null);

    int i = 0;
    int j = s.length() - 1;

    while (i < j) {
      if (s.charAt(i++) != s.charAt(j--)) {
        return false;
      }
    }

    return true;
  }

  static String reverse(String s) {
    return new StringBuffer(s).reverse().toString();
  }
}
