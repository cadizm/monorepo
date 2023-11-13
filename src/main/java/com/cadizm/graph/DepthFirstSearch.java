package com.cadizm.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;

import com.google.common.base.Preconditions;

public class DepthFirstSearch {

  public static <T> void iterativeDfs(Node<T> root, Consumer<Node<T>> visit) {
    Preconditions.checkArgument(root != null);

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
}
