package com.cadizm.aoc._2022;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.cadizm.io.Resource;

/*
 * --- Day 5: Supply Stacks ---
 * The expedition can depart as soon as the final supplies have been unloaded from the ships.
 * Supplies are stored in stacks of marked crates, but because the needed supplies are buried under
 * many other crates, the crates need to be rearranged.
 *
 * The ship has a giant cargo crane capable of moving crates between stacks. To ensure none of the
 * crates get crushed or fall over, the crane operator will rearrange them in a series of
 * carefully-planned steps. After the crates are rearranged, the desired crates will be at the top
 * of each stack.
 *
 * The Elves don't want to interrupt the crane operator during this delicate procedure, but they
 * forgot to ask her which crate will end up where, and they want to be ready to unload them as soon
 * as possible so they can embark.
 *
 * They do, however, have a drawing of the starting stacks of crates and the rearrangement procedure
 * (your puzzle input). For example:
 *
 *     [D]
 * [N] [C]
 * [Z] [M] [P]
 *  1   2   3
 *
 * move 1 from 2 to 1
 * move 3 from 1 to 3
 * move 2 from 2 to 1
 * move 1 from 1 to 2
 *
 * In this example, there are three stacks of crates. Stack 1 contains two crates: crate Z is on the
 * bottom, and crate N is on top. Stack 2 contains three crates; from bottom to top, they are crates
 * M, C, and D. Finally, stack 3 contains a single crate, P.
 *
 * Then, the rearrangement procedure is given. In each step of the procedure, a quantity of crates
 * is moved from one stack to a different stack. In the first step of the above rearrangement
 * procedure, one crate is moved from stack 2 to stack 1, resulting in this configuration:
 *
 * [D]
 * [N] [C]
 * [Z] [M] [P]
 *  1   2   3
 *
 * In the second step, three crates are moved from stack 1 to stack 3. Crates are moved one at a
 * time, so the first crate to be moved (D) ends up below the second and third crates:
 *
 *         [Z]
 *         [N]
 *     [C] [D]
 *     [M] [P]
 *  1   2   3
 *
 * Then, both crates are moved from stack 2 to stack 1. Again, because crates are moved one at a
 * time, crate C ends up below crate M:
 *
 *         [Z]
 *         [N]
 * [M]     [D]
 * [C]     [P]
 *  1   2   3
 *
 * Finally, one crate is moved from stack 1 to stack 2:
 *
 *         [Z]
 *         [N]
 *         [D]
 * [C] [M] [P]
 *  1   2   3
 *
 * The Elves just need to know which crate will end up on top of each stack; in this example, the
 * top crates are C in stack 1, M in stack 2, and Z in stack 3, so you should combine these together
 * and give the Elves the message CMZ.
 *
 * After the rearrangement procedure completes, what crate ends up on top of each stack?
 */
public class Day05SupplyStacks {

  public static String topCrates() {
    Stream<String> lines = Resource.readLines("day05.input");

    Deque<String> stackLines = new ArrayDeque<>();
    List<String> moves = new ArrayList<>();

    boolean flag = true;

    // process entire stream to gather stacks and moves
    for (var it = lines.iterator(); it.hasNext(); ) {
      String s = it.next();

      if (s.isBlank()) {  // done sentinel
        flag = false;
        continue;
      }

      if (flag) {
        stackLines.offerLast(s);
      } else {
        moves.add(s);
      }
    }

    Map<Character, Integer> indexMap = new HashMap<>();
    Map<Character, Stack<Character>> stackMap = new HashMap<>();

    String indices = stackLines.removeLast();

    // fill indexMap and initialize stackMap keys
    for (int i = 0; i < indices.length(); ++i) {
      char c = indices.charAt(i);
      if (c == ' ') {
        continue;
      }

      indexMap.put(c, i);
      stackMap.put(c, new Stack<>());
    }

    // fill stacks
    while (!stackLines.isEmpty()) {
      String s = stackLines.removeLast();

      for (var entry : indexMap.entrySet()) {
        char stack = entry.getKey();
        int index = entry.getValue();

        if (index >= s.length()) {
          continue;
        }

        char c = s.charAt(index);
        if (c != ' ') {
          stackMap.get(stack).push(c);
        }
      }
    }

    // process moves
    for (String move : moves) {
      String[] parts = move.split(" from ");
      int count = Integer.parseInt(parts[0].split(" ")[1]);

      parts = parts[1].split(" to ");
      char src = parts[0].trim().charAt(0);
      char dst = parts[1].trim().charAt(0);

      for (int i = 0; i < count; ++i) {
        stackMap.get(dst).push(stackMap.get(src).pop());
      }
    }

    // collect tops of stacks
    return stackMap.values()
        .stream()
        .map(Stack::peek)
        .map(String::valueOf)
        .collect(Collectors.joining(""));
  }

  public static String multipleTopCrates() {
    Stream<String> lines = Resource.readLines("day05.input");

    Deque<String> stackLines = new ArrayDeque<>();
    List<String> moves = new ArrayList<>();

    boolean flag = true;

    // process entire stream to gather stacks and moves
    for (var it = lines.iterator(); it.hasNext(); ) {
      String s = it.next();

      if (s.isBlank()) {  // done sentinel
        flag = false;
        continue;
      }

      if (flag) {
        stackLines.offerLast(s);
      } else {
        moves.add(s);
      }
    }

    Map<Character, Integer> indexMap = new HashMap<>();
    Map<Character, Stack<Character>> stackMap = new HashMap<>();

    String indices = stackLines.removeLast();

    // fill indexMap and initialize stackMap keys
    for (int i = 0; i < indices.length(); ++i) {
      char c = indices.charAt(i);
      if (c == ' ') {
        continue;
      }

      indexMap.put(c, i);
      stackMap.put(c, new Stack<>());
    }

    // fill stacks
    while (!stackLines.isEmpty()) {
      String s = stackLines.removeLast();

      for (var entry : indexMap.entrySet()) {
        char stack = entry.getKey();
        int index = entry.getValue();

        if (index >= s.length()) {
          continue;
        }

        char c = s.charAt(index);
        if (c != ' ') {
          stackMap.get(stack).push(c);
        }
      }
    }

    // process moves
    Stack<Character> temp = new Stack<>();
    for (String move : moves) {
      String[] parts = move.split(" from ");
      int count = Integer.parseInt(parts[0].split(" ")[1]);

      parts = parts[1].split(" to ");
      char src = parts[0].trim().charAt(0);
      char dst = parts[1].trim().charAt(0);

      for (int i = 0; i < count; ++i) {
        temp.push(stackMap.get(src).pop());
      }
      while (!temp.isEmpty()) {
        stackMap.get(dst).push(temp.pop());
      }
    }

    // collect tops of stacks
    return stackMap.values()
        .stream()
        .map(Stack::peek)
        .map(String::valueOf)
        .collect(Collectors.joining(""));
  }
}
