package nl.ramondevaan.aoc2022.day14;

import nl.ramondevaan.aoc2022.util.Parser;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class PathParser implements Parser<String, Path> {

  private final static char[] SEPARATOR = new char[]{' ', '-', '>', ' '};

  @Override
  public Path parse(final String toParse) {
    final StringCharacterIterator iterator = new StringCharacterIterator(toParse);
    final var builder = Path.builder();

    do {
      parsePoint(builder, iterator);
    } while (consumeSeparator(iterator));

    return builder.build();
  }

  private boolean consumeSeparator(final StringCharacterIterator iterator) {
    if (iterator.current() == CharacterIterator.DONE) {
      return false;
    }
    for (final var expected : SEPARATOR) {
      if (iterator.current() != expected) {
        throw new IllegalArgumentException(String.format("Unexpected token: %s", iterator.current()));
      }
      iterator.next();
    }

    return true;
  }

  private void parsePoint(final Path.Builder builder, final StringCharacterIterator iterator) {
    final int column = parseNumber(iterator);
    if (iterator.current() != ',') {
      throw new IllegalArgumentException();
    }
    iterator.next();
    final int row = parseNumber(iterator);

    builder.addPoint(row, column);
  }


  private int parseNumber(final StringCharacterIterator iterator) {
    final var builder = new StringBuilder();
    builder.append(iterator.current());

    while (iterator.next() != CharacterIterator.DONE && Character.isDigit(iterator.current())) {
      builder.append(iterator.current());
    }

    return Integer.parseInt(builder.toString());
  }
}
