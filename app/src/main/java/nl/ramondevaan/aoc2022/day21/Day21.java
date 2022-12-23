package nl.ramondevaan.aoc2022.day21;

import java.util.List;

public class Day21 {

  private final Monkeys monkeys;

  public Day21(final List<String> lines) {
    final var parser = new MonkeysParser();
    this.monkeys = parser.parse(lines);
  }

  public long solve1() {
    return monkeys.root.yell();
  }

  public long solve2() {
    if (monkeys.root.leftMonkey.contains(monkeys.human)) {
      return monkeys.root.leftMonkey.inverse(monkeys.root.rightMonkey.yell(), monkeys.human);
    }
    return monkeys.root.rightMonkey.inverse(monkeys.root.leftMonkey.yell(), monkeys.human);
  }
}
