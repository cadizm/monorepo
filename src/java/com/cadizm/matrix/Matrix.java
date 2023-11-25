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
