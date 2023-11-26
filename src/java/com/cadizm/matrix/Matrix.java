package com.cadizm.matrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

import static com.cadizm.string.Str.leftpad;

/**
 * Class representing 2-D rectangular matrix.
 */
public class Matrix<T> {

  private final T[][] matrix;

  public Matrix(T[][] matrix) {
    this.matrix = matrix;
  }

  /**
   * Construct a matrix of size rows x cols using `elements`.
   *
   * The list of elements must be of size rows * cols and are filled in
   * row-major order (left-to-right, top-to-bottom).
   */
  public Matrix(List<T> elements, int rows, int cols) {
    Preconditions.checkArgument(elements.size() == rows * cols);

    @SuppressWarnings("unchecked")
    T[][] matrix = (T[][])new Object[rows][cols];

    Iterator<T> it = elements.iterator();

    for (int row = 0; row < rows; ++row) {
      for (int col = 0; col < cols; ++col) {
        matrix[row][col] = it.next();
      }
    }

    this.matrix = matrix;
  }

  /**
   * Return a stream of all "squares" of size squareLength x squareLength in
   * the passed matrix.
   *
   * For example, given the matrix 4x4 matrix below, the following 3x3 squares
   * will be returned when this method is called with squareLength 3.
   *
   * Matrix:
   *
   *   <code>
   *      7   4   0   1
   *      5   6   2   2
   *      6  10   7   8
   *      1   4   2   0
   *   </code>
   *
   * Squares:
   *
   *   <code>
   *      7   4   0
   *      5   6   2
   *      6  10   7
   *   </code>
   *
   *   <code>
   *      4   0   1
   *      6   2   2
   *     10   7   8
   *   </code>
   *
   *   <code>
   *      5   6   2
   *      6  10   7
   *      1   4   2
   *   </code>
   *
   *   <code>
   *      6   2   2
   *     10   7   8
   *      4   2   0
   *   </code>
   */
  public static <T> Stream<List<T>> getSquares(T[][] matrix, int squareLength) {
    Preconditions.checkArgument(matrix != null);

    int rows = matrix.length;
    int cols = matrix[0].length;

    int squareRows = rows - squareLength + 1;
    int squareCols = cols - squareLength + 1;

    Stream.Builder<List<T>> stream = Stream.builder();

    for (int row = 0; row < squareRows; ++row) {
      Preconditions.checkArgument(matrix[row].length == cols);

      for (int col = 0; col < squareCols; ++col) {
        stream.add(getSquareElements(matrix, row, col, squareLength));
      }
    }

    return stream.build();
  }

  /**
   * Return the `squareLength * squareLength` elements in the "square" starting
   * at to "top-left" [row, col] position in the matrix.
   */
  static <T> List<T> getSquareElements(T[][] matrix, int row, int col, int squareLength) {
    List<T> square = new ArrayList<>();

    for (int i = row; i < row + squareLength; ++i) {
      for (int j = col; j < col + squareLength; ++j) {
        square.add(matrix[i][j]);
      }
    }

    return square;
  }

  public String toString() {
    int padLen = maxElementLength() + 2;
    StringBuilder sb = new StringBuilder();

    for (var rows : matrix) {
      StringBuilder rowBuilder = new StringBuilder();
      for (var elem : rows) {
        rowBuilder.append(leftpad(elem.toString(), padLen));
      }
      sb.append(String.format("%s\n", rowBuilder));
    }

    return sb.toString();
  }

  /**
   * Helper method that identifies the "longest" element of this matrix.
   *
   * Intended for use in pretty-printing this matrix with pleasant spacing.
   */
  int maxElementLength() {
    int max = 0;

    for (var rows : matrix) {
      for (var elem : rows) {
        max = Math.max(max, elem.toString().length());
      }
    }

    return max;
  }
}
