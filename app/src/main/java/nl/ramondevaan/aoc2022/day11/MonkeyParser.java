package nl.ramondevaan.aoc2022.day11;

import nl.ramondevaan.aoc2022.util.Parser;

import java.util.Arrays;
import java.util.List;
import java.util.function.LongUnaryOperator;

public class MonkeyParser implements Parser<List<String>, Monkey> {

  @Override
  public Monkey parse(List<String> toParse) {
    final var id = parseId(toParse.get(0));
    final var startingItems = parseStartingItems(toParse.get(1).trim());
    final var operation = parseOperation(toParse.get(2).trim());
    final var mod = parseMod(toParse.get(3).trim());
    final var ifTrue = parseIfTrue(toParse.get(4).trim());
    final var ifFalse = parseIfFalse(toParse.get(5).trim());

    return new Monkey(id, startingItems, operation, mod, ifTrue, ifFalse);
  }

  private int parseId(final String toParse) {
    if (!toParse.startsWith("Monkey ") || !toParse.endsWith(":")) {
      throw new IllegalArgumentException(String.format("Could not parse id from: %s", toParse));
    }

    return Integer.parseInt(toParse.substring(7, toParse.length() - 1));
  }

  private List<Long> parseStartingItems(final String toParse) {
    if (!toParse.startsWith("Starting items: ")) {
      throw new IllegalArgumentException(String.format("Could not parse starting items from: %s", toParse));
    }

    return Arrays.stream(toParse.substring(16).split(", ")).map(Long::parseLong).toList();
  }

  private int parseMod(final String toParse) {
    if (!toParse.startsWith("Test: divisible by ")) {
      throw new IllegalArgumentException(String.format("Could not parse test from: %s", toParse));
    }
    return Integer.parseInt(toParse.substring(19));
  }

  private int parseIfTrue(final String toParse) {
    if (!toParse.startsWith("If true: throw to monkey ")) {
      throw new IllegalArgumentException(String.format("Could not parse true condition: %s", toParse));
    }

    return Integer.parseInt(toParse.substring(25));
  }

  private int parseIfFalse(final String toParse) {
    if (!toParse.startsWith("If false: throw to monkey ")) {
      throw new IllegalArgumentException(String.format("Could not parse false condition: %s", toParse));
    }

    return Integer.parseInt(toParse.substring(26));
  }

  private LongUnaryOperator parseOperation(final String toParse) {
    final int firstSpaceIndex;
    if (!toParse.startsWith("Operation: new = ") || (firstSpaceIndex = toParse.indexOf(' ', 17)) < 0) {
      throw new IllegalArgumentException(String.format("Could not parse operation from: %s", toParse));
    }
    final String operand1 = toParse.substring(17, firstSpaceIndex);
    final char operator = toParse.charAt(firstSpaceIndex + 1);
    final String operand2 = toParse.substring(firstSpaceIndex + 3);

    return switch (operator) {
      case '+' -> parseAdd(operand1, operand2);
      case '*' -> parseMultiply(operand1, operand2);
      default -> throw new IllegalArgumentException(String.format("Could not parse operator: %s", operator));
    };
  }

  private LongUnaryOperator parseAdd(final String operand1, final String operand2) {
    if ("old".equals(operand1)) {
      if ("old".equals(operand2)) {
        return old -> old + old;
      }
      final var value = Integer.parseInt(operand2);
      return old -> old + value;
    } else if ("old".equals(operand2)) {
      final var value = Integer.parseInt(operand1);
      return old -> value + old;
    }

    final var value = Integer.parseInt(operand1) + Integer.parseInt(operand2);
    return old -> value;
  }

  private LongUnaryOperator parseMultiply(final String operand1, final String operand2) {
    if ("old".equals(operand1)) {
      if ("old".equals(operand2)) {
        return old -> old * old;
      }
      final var value = Integer.parseInt(operand2);
      return old -> old * value;
    } else if ("old".equals(operand2)) {
      final var value = Integer.parseInt(operand1);
      return old -> value * old;
    }

    final var value = Integer.parseInt(operand1) * Integer.parseInt(operand2);
    return old -> value;
  }

}
