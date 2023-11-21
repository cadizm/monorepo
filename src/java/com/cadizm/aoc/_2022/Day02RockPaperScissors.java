package com.cadizm.aoc._2022;

import java.util.stream.Stream;

import com.cadizm.io.Resource;

/*
 * --- Day 2: Rock Paper Scissors ---
 * The Elves begin to set up camp on the beach. To decide whose tent gets to be closest to the snack
 * storage, a giant Rock Paper Scissors tournament is already in progress.
 *
 * Rock Paper Scissors is a game between two players. Each game contains many rounds; in each round,
 * the players each simultaneously choose one of Rock, Paper, or Scissors using a hand shape. Then,
 * a winner for that round is selected: Rock defeats Scissors, Scissors defeats Paper, and Paper
 * defeats Rock. If both players choose the same shape, the round instead ends in a draw.
 *
 * Appreciative of your help yesterday, one Elf gives you an encrypted strategy guide (your puzzle
 * input) that they say will be sure to help you win. "The first column is what your opponent is
 * going to play: A for Rock, B for Paper, and C for Scissors. The second column--" Suddenly, the
 * Elf is called away to help with someone's tent.
 *
 * The second column, you reason, must be what you should play in response: X for Rock, Y for Paper,
 * and Z for Scissors. Winning every time would be suspicious, so the responses must have been
 * carefully chosen.
 *
 * The winner of the whole tournament is the player with the highest score. Your total score is the
 * sum of your scores for each round. The score for a single round is the score for the shape you
 * selected (1 for Rock, 2 for Paper, and 3 for Scissors) plus the score for the outcome of the
 * round (0 if you lost, 3 if the round was a draw, and 6 if you won).
 *
 * Since you can't be sure if the Elf is trying to help you or trick you, you should calculate the
 * score you would get if you were to follow the strategy guide.
 *
 * For example, suppose you were given the following strategy guide:
 *
 * A Y
 * B X
 * C Z
 * This strategy guide predicts and recommends the following:
 *
 * In the first round, your opponent will choose Rock (A), and you should choose Paper (Y). This
 * ends in a win for you with a score of 8 (2 because you chose Paper + 6 because you won).
 * In the second round, your opponent will choose Paper (B), and you should choose Rock (X). This
 * ends in a loss for you with a score of 1 (1 + 0).
 * The third round is a draw with both players choosing Scissors, giving you a score of 3 + 3 = 6.
 * In this example, if you were to follow the strategy guide, you would get a total score of 15 (8 +
 * 1 + 6).
 *
 * What would your total score be if everything goes exactly according to your strategy guide?
 */
public class Day02RockPaperScissors {

  public static long guessedStrategyGuideTotalScore() {
    Stream<String> lines = Resource.readLines("day02.input");

    long totalScore = 0;

    for (var it = lines.iterator(); it.hasNext(); ) {
      String[] parts = it.next().split(" ");
      assert parts.length == 2;

      Game game = new Game(choiceDecrypt(parts[0]), choiceDecrypt(parts[1]));
      Outcome outcome = Outcome.DRAW;

      if (game.p1Wins()) {
        outcome = Outcome.LOSS;
      } else if (game.p2Wins()) {
        outcome = Outcome.WIN;
      }

      totalScore += choiceScore(game.p2);
      totalScore += outcomeScore(outcome);
    }

    return totalScore;
  }

  /*
   * --- Part Two ---
   * The Elf finishes helping with the tent and sneaks back over to you. "Anyway, the second column
   * says how the round needs to end: X means you need to lose, Y means you need to end the round in
   * a draw, and Z means you need to win. Good luck!"
   *
   * The total score is still calculated in the same way, but now you need to figure out what shape
   * to choose so the round ends as indicated. The example above now goes like this:
   *
   * In the first round, your opponent will choose Rock (A), and you need the round to end in a draw
   * (Y), so you also choose Rock. This gives you a score of 1 + 3 = 4.
   * In the second round, your opponent will choose Paper (B), and you choose Rock so you lose (X)
   * with a score of 1 + 0 = 1.
   * In the third round, you will defeat your opponent's Scissors with Rock for a score of 1 + 6 = 7.
   * Now that you're correctly decrypting the ultra top secret strategy guide, you would get a total
   * score of 12.
   *
   * Following the Elf's instructions for the second column, what would your total score be if
   * everything goes exactly according to your strategy guide?
   */
  public static long elfStrategyGuideTotalScore() {
    Stream<String> lines = Resource.readLines("day02.input");

    long totalScore = 0;

    for (var it = lines.iterator(); it.hasNext(); ) {
      String[] parts = it.next().split(" ");
      assert parts.length == 2;

      Choice p1 = choiceDecrypt(parts[0]);
      Outcome outcome = outcomeDecrypt(parts[1]);

      Choice p2 = deduceChoice(p1, outcome);

      totalScore += choiceScore(p2);
      totalScore += outcomeScore(outcome);
    }

    return totalScore;
  }

  static Choice deduceChoice(Choice choice, Outcome outcome) {
    if (choice == Choice.ROCK && outcome == Outcome.WIN) {
      return Choice.PAPER;
    } else if (choice == Choice.ROCK && outcome == Outcome.LOSS) {
      return Choice.SCISSORS;
    } else if (choice == Choice.ROCK && outcome == Outcome.DRAW) {
      return Choice.ROCK;
    }

    if (choice == Choice.PAPER && outcome == Outcome.WIN) {
      return Choice.SCISSORS;
    } else if (choice == Choice.PAPER && outcome == Outcome.LOSS) {
      return Choice.ROCK;
    } else if (choice == Choice.PAPER && outcome == Outcome.DRAW) {
      return Choice.PAPER;
    }

    if (choice == Choice.SCISSORS && outcome == Outcome.WIN) {
      return Choice.ROCK;
    } else if (choice == Choice.SCISSORS && outcome == Outcome.LOSS) {
      return Choice.PAPER;
    } else if (choice == Choice.SCISSORS && outcome == Outcome.DRAW) {
      return Choice.SCISSORS;
    }

    throw new RuntimeException(
        String.format("Could not deduce choice from %s, %s", choice, outcome));
  }

  static Choice choiceDecrypt(String encrypted) {
    assert encrypted != null;

    switch (encrypted.trim().toUpperCase()) {
      case "A":
      case "X":
        return Choice.ROCK;
      case "B":
      case "Y":
        return Choice.PAPER;
      case "C":
      case "Z":
        return Choice.SCISSORS;
      default:
        throw new RuntimeException(String.format("Could not decrypt %s to Choice", encrypted));
    }
  }

  static Outcome outcomeDecrypt(String encrypted) {
    assert encrypted != null;

    switch (encrypted.trim().toUpperCase()) {
      case "X":
        return Outcome.LOSS;
      case "Y":
        return Outcome.DRAW;
      case "Z":
        return Outcome.WIN;
      default:
        throw new RuntimeException(String.format("Could not decrypt %s to Outcome", encrypted));
    }
  }

  enum Choice {
    ROCK,
    PAPER,
    SCISSORS
  }

  enum Outcome {
    WIN,
    LOSS,
    DRAW
  }

  static int choiceScore(Choice choice) {
    switch (choice) {
      case ROCK:
        return 1;
      case PAPER:
        return 2;
      case SCISSORS:
        return 3;
      default:
        throw new RuntimeException(String.format("Unknown choice %s", choice));
    }
  }

  static int outcomeScore(Outcome outcome) {
    switch (outcome) {
      case WIN:
        return 6;
      case LOSS:
        return 0;
      case DRAW:
        return 3;
      default:
        throw new RuntimeException(String.format("Unknown outcome %s", outcome));
    }
  }

  static class Game {
    final Choice p1;
    final Choice p2;

    public Game(Choice p1, Choice p2) {
      this.p1 = p1;
      this.p2 = p2;
    }

    public boolean isDraw() {
      return p1 == p2;
    }

    public boolean p1Wins() {
      return !isDraw() && (
          (p1 == Choice.ROCK && p2 == Choice.SCISSORS) ||
              (p1 == Choice.PAPER && p2 == Choice.ROCK) ||
              (p1 == Choice.SCISSORS && p2 == Choice.PAPER));
    }

    public boolean p2Wins() {
      return !isDraw() && (
          (p2 == Choice.ROCK && p1 == Choice.SCISSORS) ||
              (p2 == Choice.PAPER && p1 == Choice.ROCK) ||
              (p2 == Choice.SCISSORS && p1 == Choice.PAPER));
    }
  }
}
