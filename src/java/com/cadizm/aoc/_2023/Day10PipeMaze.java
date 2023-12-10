package com.cadizm.aoc._2023;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Stream;

import com.cadizm.graph.Node;
import com.cadizm.io.Resource;
import com.google.common.base.Preconditions;

// https://adventofcode.com/2023/day/10
public class Day10PipeMaze {

  enum Connection {
    NORTH,
    SOUTH,
    EAST,
    WEST
  }

  record Position(int row, int col) {}

  record Direction(Position position, Connection connection) {}

  record Tile(String val, Position position, List<Connection> connections) {
    @Override
    public String toString() {
      return val;
    }
  }

  private final Node<Tile>[][] grid;
  private final Node<Tile> root;

  public Day10PipeMaze(String inputPath) {
    this.grid = readInput(inputPath);
    this.root = buildGraph();
  }

  // read the puzzle input into a matrix
  // scan the matrix and construct a graph by finding each tile's neighbors
  // find the start tile and do a DFS, backtracking from dead ends
  // starting from start, do a DFS
  // terminate when reach node already seen
  // find length of loop and return its midpoint
  public int puzzle1() {
    List<Node<Tile>> path = dfs(root);

    return path.size() / 2;
  }

  public int puzzle2() {
    return 0;
  }

  static List<Node<Tile>> dfs(Node<Tile> root) {
    List<Node<Tile>> path = new ArrayList<>();

    Set<Node<Tile>> visited = new HashSet<>();
    Stack<Node<Tile>> stack = new Stack<>();

    stack.push(root);
    while (!stack.isEmpty()) {
      var node = stack.pop();

      if (visited.contains(node)) {
        continue;
      }

      path.add(node);
      visited.add(node);

      stack.addAll(node.getNeighbors());
    }

    return path;
  }

  /*
   * | is a vertical pipe connecting north and south
   * - is a horizontal pipe connecting east and west
   * L is a 90-degree bend connecting north and east
   * J is a 90-degree bend connecting north and west
   * 7 is a 90-degree bend connecting south and west
   * F is a 90-degree bend connecting south and east
   * . is ground; there is no pipe in this tile
   * S is the start position; there is a pipe on this tile, but you don't know the shape
   */
  List<Connection> getConnections(String val) {
    return switch (val) {
      case "|" -> List.of(Connection.NORTH, Connection.SOUTH);
      case "-" -> List.of(Connection.EAST, Connection.WEST);
      case "L" -> List.of(Connection.NORTH, Connection.EAST);
      case "J" -> List.of(Connection.NORTH, Connection.WEST);
      case "7" -> List.of(Connection.SOUTH, Connection.WEST);
      case "F" -> List.of(Connection.SOUTH, Connection.EAST);
      case "." -> List.of();
      case "S" -> List.of(Connection.NORTH, Connection.SOUTH, Connection.EAST, Connection.WEST);
      default -> throw new RuntimeException();
    };
  }

  /**
   * Return true if t1 connects to t2 using connections c1 and c2 respectively.
   */
  boolean connect(Tile t1, Tile t2, Connection c1, Connection c2) {
    return t1.connections.contains(c1) && t2.connections.contains(c2);
  }

  static boolean areNeighbors(Node<Tile> a, Node<Tile> b) {
    return a.getNeighbors().contains(b) && b.getNeighbors().contains(a);
  }

  /**
   * Return the tiles that "connect" to tile in grid.
   */
  List<Node<Tile>> getNeighbors(Node<Tile> node, int row, int col, Node<Tile>[][] grid) {
    Preconditions.checkArgument(inBounds(row, col, grid));

    List<Node<Tile>> neighbors = new ArrayList<>();

    List<Direction> directions = List.of(
        new Direction(new Position(-1, 0), Connection.NORTH), // up
        new Direction(new Position(1, 0), Connection.SOUTH),  // down
        new Direction(new Position(0, -1), Connection.WEST),  // left
        new Direction(new Position(0, 1), Connection.EAST)    // right
    );

    for (var direction : directions) {
      int i = row + direction.position.row;
      int j = col + direction.position.col;

      if (!inBounds(i, j, grid)) {
        continue;
      }

      Node<Tile> candidateNode = grid[i][j];
      Tile candidate = candidateNode.getData();

      Tile tile = node.getData();

      // adjacent tile that is in "direction" of passed tile
      boolean flag = switch (direction.connection) {
        case NORTH -> connect(tile, candidate, Connection.NORTH, Connection.SOUTH);
        case SOUTH -> connect(tile, candidate, Connection.SOUTH, Connection.NORTH);
        case EAST -> connect(tile, candidate, Connection.EAST, Connection.WEST);
        case WEST -> connect(tile, candidate, Connection.WEST, Connection.EAST);
      };

      if (flag) {
        neighbors.add(candidateNode);
      }
    }

    return neighbors;
  }

  boolean inBounds(int row, int col, Node<Tile>[][] grid) {
    return row >= 0 && row < grid.length &&
        col >= 0 && col < grid[row].length;
  }

  Node<Tile> buildGraph() {
    Node<Tile> root = null;

    for (int row = 0; row < grid.length; ++row) {
      for (int col = 0; col < grid[row].length; ++col) {
        Node<Tile> node = grid[row][col];
        node.setNeighbors(getNeighbors(node, row, col, grid));

        if (node.getLabel().equals("S")) {
          root = node;
        }
      }
    }

    if (root == null) {
      throw new RuntimeException("Start node not found");
    }

    return root;
  }

  Node<Tile> buildNode(Tile tile) {
    return new Node<>(tile.val, tile);
  }

  Node<Tile>[][] readInput(String inputPath) {
    Stream<String> stream = Resource.readLines(inputPath);

    List<String[]> lines = new ArrayList<>();

    for (var iter = stream.iterator(); iter.hasNext(); ) {
      String line = iter.next();
      lines.add(line.split(""));
    }

    int rows = lines.size();
    int cols = lines.get(0).length;

    @SuppressWarnings({"unchecked"})
    Node<Tile>[][] grid = (Node<Tile>[][])Array.newInstance(Node.class, rows, cols);

    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < cols; ++j) {
        String val = lines.get(i)[j];
        List<Connection> connections = getConnections(val);
        grid[i][j] = buildNode(new Tile(val, new Position(i, j), connections));
      }
    }

    return grid;
  }
}
