package com.cadizm.aoc._2023;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cadizm.graph.Node;
import com.cadizm.io.Resource;
import com.google.common.base.Preconditions;

import static com.cadizm.graph.DepthFirstSearch.dfs;

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

  // Read the puzzle input into a matrix
  // Scan the matrix and construct a graph by finding each tile's neighbors
  // find the start tile and do a DFS, keeping track of the path traversed
  // terminate after visiting every node along path
  // find length of loop and return its midpoint
  // Note that either BFS or DFS could be used in this case because we have
  // a predefined start node and every node has exactly 2 neighbors
  public int puzzle1() {
    List<Node<Tile>> path = dfs(root);

    return path.size() / 2;
  }

  // Part 2 had me stumped on how to handle the "squeeze through pipes" use
  // case. I gave up and looked on Reddit and saw that many others used an
  // "expanded grid" approach. This is something I was not familiar with.
  // See steps below:
  //
  // Perform dfs similar to part 1 in order to figure out which tiles are
  // part of loop path
  // Create a new grid, replacing all non-path tiles with "."
  // Create a new expanded grid that maps each tile to an expanded 3x3 tile
  // Note that this means that a 3x3 representation needs to be created for
  // every tile symbol on the path (i.e. every pipe type)
  // After expanding the grid, pick a non-path tile that is "outside" the loop
  // such as (0, 0) and do a flood-fill to replace all adjacent "." symbols,
  // making sure not to cross path boundaries
  // Finally, scan expanded grid and count all 3x3 tiles that are filled with
  // all "." symbols. These are the interior "."'s in the original graph
  public int puzzle2() {
    Set<Node<Tile>> path = new HashSet<>(dfs(root));

    int rows = grid.length;
    int cols = grid[0].length;

    // Create a new grid initialized with `.`
    String[][] buf = getEmptyGrid(rows, cols, ".");

    // Update new grid with path symbols
    for (var node : path) {
      Position pos = node.getData().position();
      String symbol = node.getLabel();
      buf[pos.row][pos.col] = symbol.equals("S") ? getRootSymbol() : symbol;
    }

    // Create a new expanded grid that maps each tile to a 3x3 set of tiles
    String[][] expanded = new String[3 * rows][3 * cols];

    for (int row = 0; row < rows; ++row) {
      for (int col = 0; col < cols; ++col) {
        String symbol = buf[row][col];
        String[][] mapping = expand(symbol);

        for (int i = 0; i < mapping.length; ++i) {
          for (int j = 0; j < mapping[i].length; ++j) {
            expanded[3 * row + i][3 * col + j] = mapping[i][j];
          }
        }
      }
    }

    // We assume that, (0, 0) is outside the path, so flood-fill from (0, 0)
    Position position = new Position(0, 0);
    String seed = expanded[position.row][position.col];

    Deque<Position> queue = new ArrayDeque<>();
    queue.add(position);

    Set<Position> visited = new HashSet<>();

    while (!queue.isEmpty()) {
      position = queue.remove();

      if (visited.contains(position)) {
        continue;
      }

      visited.add(position);
      String symbol = expanded[position.row][position.col];

      if (!symbol.equals(seed)) {
        continue;
      }

      expanded[position.row][position.col] = " ";

      for (var direction : getDirections()) {
        int dr = position.row + direction.position.row;
        int dc = position.col + direction.position.col;

        if (inBounds(dr, dc, expanded)) {
          queue.add(new Position(dr, dc));
        }
      }
    }

    int count = 0;
    String needle = ".".repeat(9);

    // Scan expanded grid and look for 3x3 tiles that are filled with `.`
    for (int row = 0; row < rows; ++row) {
      for (int col = 0; col < cols; ++col) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 3; ++i) {
          for (int j = 0; j < 3; ++j) {
            sb.append(expanded[3 * row + i][3 * col + j]);
          }
        }

        if (sb.toString().equals(needle)) {
          count += 1;
        }
      }
    }

    return count;
  }

  String[][] expand(String symbol) {
    return switch (symbol) {
      case "." -> new String[][] {
          new String[] {".", ".", "."},
          new String[] {".", ".", "."},
          new String[] {".", ".", "."},
      };
      case "-" -> new String[][] {
          new String[] {".", ".", "."},
          new String[] {"#", "#", "#"},
          new String[] {".", ".", "."},
      };
      case "|" -> new String[][] {
          new String[] {".", "#", "."},
          new String[] {".", "#", "."},
          new String[] {".", "#", "."},
      };
      case "F" -> new String[][] {
          new String[] {".", ".", "."},
          new String[] {".", "#", "#"},
          new String[] {".", "#", "."},
      };
      case "J" -> new String[][] {
          new String[] {".", "#", "."},
          new String[] {"#", "#", "."},
          new String[] {".", ".", "."},
      };
      case "7" -> new String[][] {
          new String[] {".", ".", "."},
          new String[] {"#", "#", "."},
          new String[] {".", "#", "."},
      };
      case "L" -> new String[][] {
          new String[] {".", "#", "."},
          new String[] {".", "#", "#"},
          new String[] {".", ".", "."},
      };
      default -> throw new RuntimeException();
    };
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

  String getRootSymbol() {
    Set<Position> neighborPositions = root.getNeighbors()
        .stream()
        .map(Node::getData)
        .map(Tile::position)
        .collect(Collectors.toSet());

    Tile rootTile = root.getData();
    int row = rootTile.position.row;
    int col = rootTile.position.col;

    List<Connection> connections = new ArrayList<>();

    for (var direction : getDirections()) {
      int dr = row + direction.position.row;
      int dc = col + direction.position.col;

      Position pos = new Position(dr, dc);
      if (neighborPositions.contains(pos)) {
        connections.add(direction.connection);
      }
    }

    Preconditions.checkArgument(connections.size() == 2);
    String label = String.format("%s %s", connections.get(0), connections.get(1));

    /*
     * .....
     * .F-7.
     * .|.|.
     * .L-J.
     * .....
     * NORTH SOUTH -> |
     * EAST WEST   -> -
     * NORTH EAST  -> L
     * NORTH WEST  -> J
     * SOUTH EAST  -> F
     * SOUTH WEST  -> 7
     */
    return switch (label) {
      case "NORTH SOUTH" -> "|";
      case "EAST WEST" -> "-";
      case "NORTH EAST" -> "L";
      case "NORTH WEST" -> "J";
      case "SOUTH EAST" -> "F";
      case "SOUTH WEST" -> "7";
      default -> throw new RuntimeException();
    };
  }

  /**
   * Return true if t1 connects to t2 using connections c1 and c2 respectively.
   */
  boolean connect(Tile t1, Tile t2, Connection c1, Connection c2) {
    return t1.connections.contains(c1) && t2.connections.contains(c2);
  }

  List<Direction> getDirections() {
    return List.of(
        new Direction(new Position(-1, 0), Connection.NORTH), // up
        new Direction(new Position(1, 0), Connection.SOUTH),  // down
        new Direction(new Position(0, -1), Connection.WEST),  // left
        new Direction(new Position(0, 1), Connection.EAST)    // right
    );
  }

  /**
   * Return the tiles that "connect" to tile in grid.
   */
  List<Node<Tile>> getNeighbors(Node<Tile> node, int row, int col, Node<Tile>[][] grid) {
    Preconditions.checkArgument(inBounds(row, col, grid));

    List<Node<Tile>> neighbors = new ArrayList<>();

    for (var direction : getDirections()) {
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

  <T> boolean inBounds(int row, int col, T[][] grid) {
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

  String[][] getEmptyGrid(int rows, int cols, String seed) {
    String[][] grid = (String[][])Array.newInstance(String.class, rows, cols);
    for (int i = 0; i < rows; ++i) {
      Arrays.fill(grid[i], seed);
    }
    return grid;
  }
}
