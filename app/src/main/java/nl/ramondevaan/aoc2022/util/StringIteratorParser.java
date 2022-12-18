package nl.ramondevaan.aoc2022.util;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class StringIteratorParser {

  private final StringCharacterIterator iterator;

  public StringIteratorParser(final String toParse) {
    this.iterator = new StringCharacterIterator(toParse);
  }

  public boolean consumeIfPresent(final char character) {
    if (iterator.current() == character) {
      iterator.next();
      return true;
    }

    return false;
  }

  public boolean tryConsume(final char[] chars) {
    if (iterator.current() == CharacterIterator.DONE) {
      return false;
    }
    for (final var expected : chars) {
      if (iterator.current() != expected) {
        throw new IllegalArgumentException(String.format("Unexpected token: %s", iterator.current()));
      }
      iterator.next();
    }

    return true;
  }

  public void consume(final char character) {
    if (iterator.current() != character) {
      throw new IllegalArgumentException(String.format("Unexpected token: %s", iterator.current()));
    }
    iterator.next();
  }

  public void consume(final char[] chars) {
    for (final var expected : chars) {
      if (iterator.current() != expected) {
        throw new IllegalArgumentException(String.format("Unexpected token: %s", iterator.current()));
      }
      iterator.next();
    }
  }

  public int parseNumber() {
    final var builder = new StringBuilder();
    builder.append(iterator.current());

    while (iterator.next() != CharacterIterator.DONE && Character.isDigit(iterator.current())) {
      builder.append(iterator.current());
    }

    return Integer.parseInt(builder.toString());
  }

  public byte parseByte() {
    final var builder = new StringBuilder();
    builder.append(iterator.current());

    while (iterator.next() != CharacterIterator.DONE && Character.isDigit(iterator.current())) {
      builder.append(iterator.current());
    }

    return Byte.parseByte(builder.toString());
  }

  public String parseString() {
    final var builder = new StringBuilder();
    builder.append(iterator.current());

    while (iterator.next() != CharacterIterator.DONE && Character.isAlphabetic(iterator.current())) {
      builder.append(iterator.current());
    }

    return builder.toString();
  }
}
