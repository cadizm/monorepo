package com.cadizm.graph;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
  private final T data;
  private List<Node<T>> neighbors;

  public Node(T data) {
    this(data, new ArrayList<>());
  }

  public Node(T data, List<Node<T>> neighbors) {
    this.data = data;
    this.neighbors = neighbors;
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
    return String.format("(%s)", data);
  }
}
