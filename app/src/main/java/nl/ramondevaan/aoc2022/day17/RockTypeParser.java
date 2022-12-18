package nl.ramondevaan.aoc2022.day17;

import nl.ramondevaan.aoc2022.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2022.util.IntMap;
import nl.ramondevaan.aoc2022.util.Parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public class RockTypeParser implements Parser<URL, List<IntMap>> {

  private final BlankStringPartitioner partitioner;

  public RockTypeParser() {
    partitioner = new BlankStringPartitioner();
  }

  @Override
  public List<IntMap> parse(URL url) {
    return getPartitions(url).stream().map(this::parseRockType).toList();
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

  private IntMap parseRockType(final List<String> toParse) {
    final var rows = toParse.size();
    final var columns = rows == 0 ? 0 : toParse.get(0).length();
    final var builder = IntMap.builder(rows, columns);

    final var iterator = toParse.iterator();
    for (int row = toParse.size() - 1; row >= 0; row--) {
      final var line = iterator.next();
      for (int column = 0; column < line.length(); column++) {
        switch (line.charAt(column)) {
          case '#':
            builder.set(row, column, 1);
          case '.':
            break;
          default:
            throw new IllegalArgumentException();
        }
      }
    }

    return builder.build();
  }
}
