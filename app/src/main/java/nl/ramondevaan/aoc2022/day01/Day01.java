package nl.ramondevaan.aoc2022.day01;

import nl.ramondevaan.aoc2022.util.BlankStringPartitioner;

import java.util.Comparator;
import java.util.List;

public class Day01 {

  private final List<List<Long>> itemCaloriesPerElf;

  public Day01(final List<String> lines) {
    final var partitioner = new BlankStringPartitioner();
    final var partitions = partitioner.partition(lines);

    this.itemCaloriesPerElf = partitions.stream()
        .map(list -> list.stream().map(Long::parseLong).toList())
        .toList();
  }

  public long solve1() {
    return itemCaloriesPerElf.stream()
        .mapToLong(list -> list.stream().mapToLong(Long::longValue).sum())
        .max()
        .orElseThrow();
  }

  public long solve2() {
    return itemCaloriesPerElf.stream()
        .map(list -> list.stream().mapToLong(Long::longValue).sum())
        .sorted(Comparator.reverseOrder())
        .mapToLong(Long::longValue)
        .limit(3)
        .sum();
  }
}
