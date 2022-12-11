package nl.ramondevaan.aoc2022.day10;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2022.util.Letter;
import nl.ramondevaan.aoc2022.util.Parser;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class LettersParser implements Parser<boolean[][], String> {

  private final List<Letter> letters;

  @Override
  public String parse(final boolean[][] toParse) {
    final var columns = toParse[0].length;
    final var gaps = IntStream.range(0, columns)
        .filter(column -> IntStream.range(0, toParse.length).noneMatch(row -> toParse[row][column]))
        .toArray();

    final var builder = new StringBuilder();
    var from = 0;
    for (final var gap : gaps) {
      builder.append(parseLetter(toParse, from, gap - from).letter());
      from = gap + 1;
    }

    return builder.toString();
  }

  private Letter parseLetter(final boolean[][] toParse, final int fromColumn, final int size) {
    for (final var letter : letters) {
      if (matches(letter, toParse, fromColumn, size)) {
        return letter;
      }
    }

    throw new IllegalArgumentException("Could not parse letter from input");
  }

  private boolean matches(final Letter letter, final boolean[][] toParse, final int fromColumn, final int size) {
    if (letter.pattern().rows() != toParse.length) {
      return false;
    }
    final var pattern = letter.pattern();

    for (int letterColumn = 0, testColumn = fromColumn; letterColumn < size; letterColumn++, testColumn++) {
      for (int row = 0; row < toParse.length; row++) {
        if (toParse[row][testColumn] != (pattern.valueAt(row, letterColumn) > 0)) {
          return false;
        }
      }
    }

    return true;
  }
}
