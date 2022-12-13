package nl.ramondevaan.aoc2022.day13;

import nl.ramondevaan.aoc2022.util.Parser;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.List;

public class PacketPairParser implements Parser<List<String>, PacketPair> {
  @Override
  public PacketPair parse(final List<String> toParse) {
    return new PacketPair(parseLine(toParse.get(0)), parseLine(toParse.get(1)));
  }

  private DataList parseLine(final String toParse) {
    final var iterator = new StringCharacterIterator(toParse);

    if (iterator.current() != '[') {
      throw new IllegalArgumentException("Line must be a list");
    }

    final var list = parseList(iterator);

    if (iterator.current() != CharacterIterator.DONE) {
      throw new IllegalArgumentException("Finished parsing list, but there was still more input");
    }

    return list;
  }

  private DataList parseList(final StringCharacterIterator iterator) {
    final var list = DataList.builder();
    char current;
    iterator.next();

    while ((current = iterator.current()) != CharacterIterator.DONE) {
      if (current == '[') {
        list.add(parseList(iterator));
      } else if (current == ']') {
        iterator.next();
        return list.build();
      } else if (Character.isDigit(current)) {
        list.add(parseNumber(iterator));
      } else if (current == ',') {
        iterator.next();
      } else {
        throw new IllegalArgumentException(String.format("Unexpected symbol: %s", current));
      }
    }

    throw new IllegalArgumentException("Input ended before list was closed");
  }

  private DataInteger parseNumber(final StringCharacterIterator iterator) {
    final var builder = new StringBuilder();
    builder.append(iterator.current());

    while (iterator.next() != CharacterIterator.DONE && Character.isDigit(iterator.current())) {
      builder.append(iterator.current());
    }

    return new DataInteger(Integer.parseInt(builder.toString()));
  }
}
