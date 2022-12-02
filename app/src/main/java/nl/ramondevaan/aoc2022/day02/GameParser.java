package nl.ramondevaan.aoc2022.day02;

import nl.ramondevaan.aoc2022.util.Parser;

public class GameParser implements Parser<String, Game> {

  @Override
  public Game parse(String toParse) {
    return new Game(toParse.charAt(0), toParse.charAt(2));
  }
}
