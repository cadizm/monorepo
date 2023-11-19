package com.cadizm.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static com.cadizm.graph.DepthFirstSearch.allPaths;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DepthFirstSearchTest {

  @Test
  public void testAllPathsDfs() {
    var aba = new Node<>("aba");
    var bbb = new Node<>("bbb");
    var bab = new Node<>("bab");

    aba.setNeighbors(List.of(bbb, bab));
    bbb.setNeighbors(List.of(aba, bab));
    bab.setNeighbors(List.of(aba, bbb));

    List<List<String>> allPermutations = new ArrayList<>();

    for (var node : List.of(aba, bbb, bab)) {
      Map<Integer, List<List<String>>> paths = allPaths(node);
      List<List<String>> longestPaths = paths.get(3);
      allPermutations.addAll(longestPaths);
    }

    assertEquals(6, allPermutations.size());

    assertThat(allPermutations, containsInAnyOrder(
        List.of("aba", "bbb", "bab"),
        List.of("aba", "bab", "bbb"),
        List.of("bbb", "aba", "bab"),
        List.of("bbb", "bab", "aba"),
        List.of("bab", "aba", "bbb"),
        List.of("bab", "bbb", "aba")
    ));
  }
}
