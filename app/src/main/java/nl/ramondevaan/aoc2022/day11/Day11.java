package nl.ramondevaan.aoc2022.day11;

import nl.ramondevaan.aoc2022.util.BlankStringPartitioner;

import java.util.*;
import java.util.function.LongUnaryOperator;

public class Day11 {

  final List<Monkey> monkeys;

  public Day11(final List<String> lines) {
    final var partitioner = new BlankStringPartitioner();
    final var parser = new MonkeyParser();

    this.monkeys = partitioner.partition(lines).stream()
        .map(parser::parse)
        .sorted(Comparator.comparingInt(Monkey::id))
        .toList();
  }

  public long solve1() {
    return solve(20, worryLevel -> worryLevel / 3);
  }

  public long solve2() {
    final var modAll = monkeys.stream().mapToLong(Monkey::mod).reduce((a, b) -> a * b).orElseThrow();
    return solve(10_000, worryLevel -> worryLevel % modAll);
  }

  private long solve(final int rounds, final LongUnaryOperator worryLevelOperator) {
    final Deque<Long>[] itemsByMonkey = (Deque<Long>[]) monkeys.stream()
        .map(monkey -> new ArrayDeque<>(monkey.startingItems())).toArray(Deque[]::new);
    final var inspectionCount = new int[monkeys.size()];

    for (int round = 0; round < rounds; round++) {
      for (int i = 0; i < monkeys.size(); i++) {
        final var monkey = monkeys.get(i);
        final var items = itemsByMonkey[i];
        inspectionCount[i] += items.size();
        while (!items.isEmpty()) {
          final var worryLevel = monkey.operation().andThen(worryLevelOperator).applyAsLong(items.pop());
          final int throwIndex = worryLevel % monkey.mod() == 0 ? monkey.ifTrue() : monkey.ifFalse();
          itemsByMonkey[throwIndex].add(worryLevel);
        }
      }
    }

    Arrays.sort(inspectionCount);
    return (long) inspectionCount[monkeys.size() - 2] * inspectionCount[monkeys.size() - 1];
  }
}
