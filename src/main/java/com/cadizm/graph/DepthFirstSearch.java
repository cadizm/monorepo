package com.cadizm.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;

import com.google.common.base.Preconditions;

public class DepthFirstSearch {

  /**
   * Perform iterative depth-first traversal of graph rooted at `root`.
   *
   * @param root  Node from which to initiative DFS traversal
   * @param visit Consumer functional interface applied to each node during traversal
   */
  public static <T> void iterativeDfs(Node<T> root, Consumer<Node<T>> visit) {
    Preconditions.checkArgument(root != null & visit != null);

    Map<Node<T>, Boolean> visited = new HashMap<>();

    Stack<Node<T>> stack = new Stack<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      var node = stack.pop();

      if (visited.containsKey(node)) {
        continue;
      }

      visit.accept(node);
      visited.put(node, true);

      for (var neighbor : node.getNeighbors()) {
        stack.push(neighbor);
      }
    }
  }

  public static <T> Map<Integer, List<List<String>>> allPaths(Node<T> root) {
    Preconditions.checkArgument(root != null);

    Map<Integer, List<List<String>>> paths = new HashMap<>();

    allPathsHelper(root, new HashMap<>(), new Stack<>(), paths);

    return paths;
  }

  /**
   * Recursive helper method for `allPaths(Node)` to traverse all paths in the graph
   * rooted at `root`.
   *
   * @param visited Internal map used to maintain state of nodes visited during traversal
   * @param path    List of elements visited during traversal in FIFO order
   * @param paths   Map of all paths of length n -> nodes traversed in path
   */
  static <T> void allPathsHelper(Node<T> root, Map<Node<T>, Boolean> visited,
      Stack<Node<T>> path, Map<Integer, List<List<String>>> paths) {

    // This method should only be invoked on unvisited nodes
    Preconditions.checkArgument(!visited.containsKey(root));

    path.push(root);

    for (var neighbor : root.getNeighbors()) {
      if (visited.containsKey(neighbor)) {
        continue;
      }

      visited.put(root, true);
      allPathsHelper(neighbor, visited, path, paths);
      visited.remove(root);  // mark unvisited to revisit along alternate path
    }

    // Add this path to the list of paths at current path length
    paths.computeIfAbsent(path.size(), v -> new ArrayList<>())
        .add(gatherLabels(path));

    path.pop();
  }

  /**
   * Return a list of the labels in this stack _without modifying_ the stack itself.
   * The list of labels are returned in iterator order.
   */
  static <T> List<String> gatherLabels(Stack<Node<T>> stack) {
    Preconditions.checkArgument(stack != null);

    List<String> res = new ArrayList<>();

    for (var node : stack) {
      res.add(node.getLabel());
    }

    return res;
  }
}
