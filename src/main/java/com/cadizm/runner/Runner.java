package com.cadizm.runner;

import java.util.List;

import com.cadizm.graph.DepthFirstSearch;
import com.cadizm.graph.Node;

public class Runner {

  public static void stringsRearrangement() {
    var aba = new Node<>("aba");
    var bbb = new Node<>("bbb");
    var bab = new Node<>("bab");

    aba.setNeighbors(List.of(bbb, bab));
    bbb.setNeighbors(List.of(aba, bab));
    bab.setNeighbors(List.of(aba, bbb));

    System.out.printf("Performing iterative DFS rooted iterative %s\n", aba);

    DepthFirstSearch.iterativeDfs(aba, node -> {
      System.out.printf("Visiting %s\n", node);
    });
  }

  public static void main(String args[]) {
    stringsRearrangement();
  }
}
