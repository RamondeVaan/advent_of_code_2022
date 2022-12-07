package nl.ramondevaan.aoc2022.day07;

import java.util.List;

public class Day07 {

  private final FileSystem fileSystem;

  public Day07(final List<String> lines) {
    final var parser = new FileSystemParser();
    this.fileSystem = parser.parse(lines);
  }

  public long solve1() {
    return fileSystem.directories().stream().mapToLong(Directory::size).filter(size -> size < 100_000).sum();
  }

  public long solve2() {
    final var total = 70_000_000L;
    final var needed = 30_000_000L;

    final var used = fileSystem.root().size();
    final var free = total - used;
    final var minimumToFree = needed - free;

    return fileSystem.directories().stream()
        .mapToLong(Directory::size)
        .sorted()
        .filter(size -> size > minimumToFree)
        .findFirst()
        .orElseThrow();
  }
}
