package nl.ramondevaan.aoc2022.day14;

import nl.ramondevaan.aoc2022.util.Parser;
import nl.ramondevaan.aoc2022.util.StringIteratorParser;

public class PathParser implements Parser<String, Path> {

  private final static char[] SEPARATOR = new char[]{' ', '-', '>', ' '};

  @Override
  public Path parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);
    final var builder = Path.builder();

    do {
      parsePoint(builder, parser);
    } while (parser.consumeIfNotDone(SEPARATOR));

    return builder.build();
  }

  private void parsePoint(final Path.Builder builder, final StringIteratorParser parser) {
    final int column = parser.parseNumber();
    parser.consume(',');
    final int row = parser.parseNumber();

    builder.addPoint(row, column);
  }
}
