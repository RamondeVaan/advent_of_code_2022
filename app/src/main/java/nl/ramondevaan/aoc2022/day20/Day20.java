package nl.ramondevaan.aoc2022.day20;

import java.util.ArrayList;
import java.util.List;

public class Day20 {

  private final List<Long> encryptedFile;

  public Day20(final List<String> lines) {
    this.encryptedFile = lines.stream().map(Long::parseLong).toList();
  }

  public long solve1() {
    final var mixed = mix(encryptedFile, 1);

    return getScore(mixed);
  }

  public long solve2() {
    final var encryptedFileTimes10 = encryptedFile.stream().map(i -> i * 811589153).toList();
    final var mixed = mix(encryptedFileTimes10, 10);

    return getScore(mixed);
  }

  private List<Long> mix(List<Long> encryptedFile, int times) {
    final var entries = new ArrayList<Entry>();
    final var list = new ArrayList<Entry>();
    final var size = encryptedFile.size() - 1;

    for (int i = 0; i < encryptedFile.size(); i++) {
      final var entry = new Entry(i, encryptedFile.get(i));
      entries.add(entry);
      list.add(entry);
    }

    for (int time = 0; time < times; time++) {
      mix(entries, list, size);
    }

    return list.stream().map(entry -> entry.number).toList();
  }

  private void mix(List<Entry> entries, List<Entry> list, final int size) {
    for (final var entry : entries) {
      var index = list.indexOf(entry);
      list.remove(index);
      var newIndex = (int) ((index + entry.number) % size);
      if (newIndex < 0) {
        newIndex += size;
      }
      list.add(newIndex, entry);
    }
  }

  private long getScore(List<Long> mixed) {
    final var indexOf0 = mixed.indexOf(0L);

    return mixed.get((indexOf0 + 1000) % mixed.size()) +
        mixed.get((indexOf0 + 2000) % mixed.size()) +
        mixed.get((indexOf0 + 3000) % mixed.size());
  }
}
