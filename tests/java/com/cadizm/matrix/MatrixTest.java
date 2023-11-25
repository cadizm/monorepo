package com.cadizm.matrix;

import java.util.List;

import org.junit.jupiter.api.Test;

import static com.cadizm.matrix.Matrix.getSquares;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MatrixTest {

  @Test
  public void testToString1x1Matrix() {
    Integer[][] matrix = new Integer[][] {
        new Integer[] {1}
    };

    assertEquals("""
              1
            """,
        new Matrix<>(matrix).toString());
  }

  @Test
  public void testToString1x2Matrix() {
    Integer[][] matrix = new Integer[][] {
        new Integer[] {1, 2}
    };

    assertEquals("""
              1  2
            """,
        new Matrix<>(matrix).toString());
  }

  @Test
  public void testToString2x1Matrix() {
    Integer[][] matrix = new Integer[][] {
        new Integer[] {1},
        new Integer[] {2},
    };

    assertEquals("""
              1
              2
            """,
        new Matrix<>(matrix).toString());
  }

  @Test
  public void testToString2x2Matrix() {
    Integer[][] matrix = new Integer[][] {
        new Integer[] {1, 2},
        new Integer[] {3, 4},
    };

    assertEquals("""
              1  2
              3  4
            """,
        new Matrix<>(matrix).toString());
  }

  @Test
  public void testToString3x4Matrix() {
    Integer[][] matrix = new Integer[][] {
        new Integer[] {1, 2, 3, 4},
        new Integer[] {5, 6, 7, 8},
        new Integer[] {9, 10, 11, 12}
    };

    assertEquals("""
               1   2   3   4
               5   6   7   8
               9  10  11  12
            """,
        new Matrix<>(matrix).toString());
  }

  @Test
  public void testGet3x3SquareFrom3x3() {
    Integer[][] matrix = new Integer[][] {
        new Integer[] {1, 1, 1},
        new Integer[] {1, 7, 1},
        new Integer[] {1, 1, 1},
    };

    List<String> expected = List.of("""
          1  1  1
          1  7  1
          1  1  1
        """
    );

    List<String> actual = getSquares(matrix, 3)
        .map(list -> new Matrix<>(list, 3, 3))
        .map(Matrix::toString)
        .toList();

    assertEquals(expected, actual);
  }

  @Test
  public void testGet3x3SquaresFrom4x4() {
    Integer[][] matrix = new Integer[][] {
        new Integer[] {7, 4, 0, 1},
        new Integer[] {5, 6, 2, 2},
        new Integer[] {6, 10, 7, 8},
        new Integer[] {1, 4, 2, 0},
    };

    List<String> expected = List.of("""
               7   4   0
               5   6   2
               6  10   7
            """,
        """
               4   0   1
               6   2   2
              10   7   8
            """,
        """
               5   6   2
               6  10   7
               1   4   2
            """,
        """
               6   2   2
              10   7   8
               4   2   0
            """
    );

    List<String> actual = getSquares(matrix, 3)
        .map(list -> new Matrix<>(list, 3, 3))
        .map(Matrix::toString)
        .toList();

    assertEquals(expected, actual);
  }

  @Test
  public void testGet2x2SquaresFrom5x3() {
    Integer[][] matrix = new Integer[][] {
        new Integer[] {1, 2, 1},
        new Integer[] {2, 2, 2},
        new Integer[] {2, 2, 2},
        new Integer[] {1, 2, 3},
        new Integer[] {2, 2, 1}
    };

    List<String> expected = List.of("""
              1  2
              2  2
            """,
        """
              2  1
              2  2
            """,
        """
              2  2
              2  2
            """,
        """
              2  2
              2  2
            """,
        """
              2  2
              1  2
            """,
        """
              2  2
              2  3
            """,
        """
              1  2
              2  2
            """,
        """
              2  3
              2  1
            """
    );

    List<String> actual = getSquares(matrix, 2)
        .map(list -> new Matrix<>(list, 2, 2))
        .map(Matrix::toString)
        .toList();

    assertEquals(expected, actual);
  }
}
