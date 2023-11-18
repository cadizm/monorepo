package com.cadizm.graph;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
  private final String label;
  private List<Node<T>> neighbors;

  public Node(String label) {
    this(label, new ArrayList<>());
  }

  public Node(String label, List<Node<T>> neighbors) {
    this.label = label;
    this.neighbors = neighbors;
  }

  public String getLabel() {
    return label;
  }

  public List<Node<T>> getNeighbors() {
    return neighbors;
  }

  public void setNeighbors(List<Node<T>> neighbors) {
    this.neighbors = neighbors;
  }

  public String toString() {
    return label;
  }
}
