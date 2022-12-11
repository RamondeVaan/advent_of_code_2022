package nl.ramondevaan.aoc2022.day11;

import nl.ramondevaan.aoc2022.util.BlankStringPartitioner;

import java.nio.LongBuffer;
import java.util.*;
import java.util.function.LongUnaryOperator;

public class Day11 {

  final List<Monkey> monkeys;
  final int totalItems;

  public Day11(final List<String> lines) {
    final var partitioner = new BlankStringPartitioner();
    final var parser = new MonkeyParser();

    this.monkeys = partitioner.partition(lines).stream()
        .map(parser::parse)
        .sorted(Comparator.comparingInt(Monkey::id))
        .toList();
    this.totalItems = Math.toIntExact(monkeys.stream().mapToLong(monkey -> monkey.startingItems().size()).sum());
  }

  public long solve1() {
    return solve(20, worryLevel -> worryLevel / 3);
  }

  public long solve2() {
    final var modAll = monkeys.stream().mapToLong(Monkey::mod).reduce((a, b) -> a * b).orElseThrow();
    return solve(10_000, worryLevel -> worryLevel % modAll);
  }

  private long solve(final int rounds, final LongUnaryOperator worryLevelOperator) {
    final LongBuffer[] itemsByMonkey = getMutableItemsByMonkey();
    final var inspectionCount = new int[monkeys.size()];

    for (int round = 0; round < rounds; round++) {
      for (int i = 0; i < monkeys.size(); i++) {
        final var monkey = monkeys.get(i);
        final var items = itemsByMonkey[i];
        final var size = items.position();
        inspectionCount[i] += size;
        items.rewind();
        for (int j = 0; j < size; j++) {
          final var worryLevel = monkey.operation().andThen(worryLevelOperator).applyAsLong(items.get());
          final int throwIndex = worryLevel % monkey.mod() == 0 ? monkey.ifTrue() : monkey.ifFalse();
          itemsByMonkey[throwIndex].put(worryLevel);
        }
        items.rewind();
      }
    }

    Arrays.sort(inspectionCount);
    return (long) inspectionCount[monkeys.size() - 2] * inspectionCount[monkeys.size() - 1];
  }

  private LongBuffer[] getMutableItemsByMonkey() {
    final var mutableItemsByMonkey = new LongBuffer[monkeys.size()];

    for (int i = 0; i < monkeys.size(); i++) {
      final var monkey = monkeys.get(i);
      final var buffer = LongBuffer.allocate(totalItems);
      for (final var itemWorryLevel : monkey.startingItems()) {
        buffer.put(itemWorryLevel);
      }
      mutableItemsByMonkey[i] = buffer;
    }

    return mutableItemsByMonkey;
  }
}
