package nl.ramondevaan.aoc2022.day02;

import java.util.List;
import java.util.Map;

public class Day02 {

  private final List<Game> games;

  public Day02(final List<String> lines) {
    final var parser = new GameParser();

    this.games = lines.stream().map(parser::parse).toList();
  }

  public long solve1() {
    final var scoreMap = Map.of(
        new Game('A', 'X'), 4,
        new Game('A', 'Y'), 8,
        new Game('A', 'Z'), 3,

        new Game('B', 'X'), 1,
        new Game('B', 'Y'), 5,
        new Game('B', 'Z'), 9,

        new Game('C', 'X'), 7,
        new Game('C', 'Y'), 2,
        new Game('C', 'Z'), 6
    );

    return games.stream().mapToLong(scoreMap::get).sum();
  }

  public long solve2() {
    final var scoreMap = Map.of(
        new Game('A', 'X'), 3,
        new Game('A', 'Y'), 4,
        new Game('A', 'Z'), 8,

        new Game('B', 'X'), 1,
        new Game('B', 'Y'), 5,
        new Game('B', 'Z'), 9,

        new Game('C', 'X'), 2,
        new Game('C', 'Y'), 6,
        new Game('C', 'Z'), 7
    );

    return games.stream().mapToLong(scoreMap::get).sum();
  }
}
