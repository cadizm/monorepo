package com.cadizm.runners;

import java.util.List;

import com.cadizm.graph.Node;

public class GraphRunner {

  public static void dfs() {
    var aba = new Node<>("aba");
    var bbb = new Node<>("bbb");
    var bab = new Node<>("bab");

    aba.setNeighbors(List.of(bbb, bab));
    bbb.setNeighbors(List.of(aba, bab));
    bab.setNeighbors(List.of(aba, bbb));

    var nodes = List.of(aba, bbb, bab);

    for (var node : nodes) {
      System.out.printf("Performing DFS rooted at %s\n", node);
    }
  }

  public static void main(String args[]) {
    dfs();
    System.out.println("\nDone");
  }
}
