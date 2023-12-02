package com.cadizm.aoc._2023;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import com.cadizm.io.Resource;

// https://adventofcode.com/2023/day/2
public class Day02CubeConundrum {

  record Cube(String color, int count) {}

  record CubeSet(List<Cube> cubes) {}

  record Game(int id, List<CubeSet> cubeSets) {}

  // Part 1 is straightforward and the most time-consuming part of the puzzle
  // is parsing the game input.
  public int puzzle1() {
    Stream<String> lines = Resource.readLines("day02.input");

    int sum = 0;

    for (var iter = lines.iterator(); iter.hasNext(); ) {
      String line = iter.next();
      Game game = parseLine(line);

      boolean valid = game.cubeSets()
          .stream()
          .allMatch(this::validCubeSet);

      if (valid) {
        sum += game.id();
      }
    }

    return sum;
  }

  // Part 2 is also straightforward. The gist of what must be done is to find the
  // maximum number of cubes used in each cube set for each game. This is performed
  // in `minCubeSet`.
  public int puzzle2() {
    Stream<String> lines = Resource.readLines("day02.input");

    int sum = 0;

    for (var iter = lines.iterator(); iter.hasNext(); ) {
      String line = iter.next();
      Game game = parseLine(line);

      sum += calculatePower(minCubeSet(game));
    }

    return sum;
  }

  int calculatePower(CubeSet cubeSet) {
    return cubeSet.cubes()
        .stream()
        .map(Cube::count)
        .reduce(1, (a, b) -> a * b);
  }

  CubeSet minCubeSet(Game game) {
    Map<String, Integer> map = new HashMap<>();

    for (CubeSet cubeSet : game.cubeSets()) {
      for (Cube cube : cubeSet.cubes()) {
        int count = map.getOrDefault(cube.color(), 0);
        map.put(cube.color(), Math.max(count, cube.count()));
      }
    }

    List<Cube> cubes = map.entrySet()
        .stream()
        .map(entry -> new Cube(entry.getKey(), entry.getValue()))
        .toList();

    return new CubeSet(cubes);
  }

  boolean validCubeSet(CubeSet cubeSet) {
    return cubeSet.cubes()
        .stream()
        .allMatch(this::validCube);
  }

  boolean validCube(Cube cube) {
    return switch (cube.color()) {
      case "red" -> cube.count() <= 12;
      case "green" -> cube.count() <= 13;
      case "blue" -> cube.count() <= 14;
      default -> true;
    };
  }

  Game parseLine(String s) {
    String regex = "^Game (?<id>\\d+): (?<rest>.*?)$";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(s);

    if (!matcher.matches()) {
      throw new RuntimeException("No matches found for: " + s);
    }

    int id = Integer.parseInt(matcher.group("id"));
    String rest = matcher.group("rest");
    String[] sets = rest.split("; ");
    List<CubeSet> cubeSets = parseSets(sets);

    return new Game(id, cubeSets);
  }

  List<CubeSet> parseSets(String[] sets) {
    return Arrays.stream(sets)
        .map(this::parseSet)
        .toList();
  }

  CubeSet parseSet(String s) {
    return new CubeSet(Arrays.stream(s.split(", "))
        .map(this::parseCube)
        .toList());
  }

  Cube parseCube(String s) {
    String[] parts = s.split("\\s+");
    return new Cube(parts[1], Integer.parseInt(parts[0]));
  }
}
