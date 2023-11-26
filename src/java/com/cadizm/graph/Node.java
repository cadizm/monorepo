package com.cadizm.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Node class representing nodes in a graph.
 *
 * Nodes are identified using labeled strings and hold data of type T.
 */
public class Node<T> {
  private final String label;
  private final T data;
  private List<Node<T>> neighbors;

  public Node(String label) {
    this(label, null, new ArrayList<>());
  }

  public Node(String label, T data) {
    this(label, data, new ArrayList<>());
  }

  public Node(String label, T data, List<Node<T>> neighbors) {
    this.label = label;
    this.data = data;
    this.neighbors = neighbors;
  }

  public String getLabel() {
    return label;
  }

  public T getData() {
    return data;
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
