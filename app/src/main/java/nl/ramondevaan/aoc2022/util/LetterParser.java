package nl.ramondevaan.aoc2022.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

public class LetterParser implements Parser<URL, List<Letter>> {

  private final BlankStringPartitioner partitioner;

  public LetterParser() {
    partitioner = new BlankStringPartitioner();
  }

  @Override
  public List<Letter> parse(URL url) {
    return getPartitions(url).stream().map(this::parseLetter).toList();
  }

  private List<List<String>> getPartitions(URL url) {
    try {
      final var path = Path.of(Objects.requireNonNull(url).toURI());
      final var lines = Files.readAllLines(path);
      return partitioner.partition(lines);
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Letter parseLetter(final List<String> toParse) {
    final var letter = toParse.get(0);
    final var pattern = parsePattern(toParse.subList(1, toParse.size()));

    return new Letter(letter, pattern);
  }

  private IntMap parsePattern(final List<String> toParse) {
    final int columns = toParse.stream().mapToInt(String::length).max().orElseThrow();

    return new IntMap(toParse.stream().map(row -> parseRow(row, columns)));
  }

  private IntStream parseRow(final String toParse, final int columns) {
    final int[] row = new int[columns];

    for (int column = 0; column < toParse.length(); column++) {
      row[column] = Character.isSpaceChar(toParse.charAt(column)) ? 0 : 1;
    }

    return IntStream.of(row);
  }
}
