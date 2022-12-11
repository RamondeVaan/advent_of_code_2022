package nl.ramondevaan.aoc2022.day11;

import java.util.List;
import java.util.function.LongUnaryOperator;

public record Monkey(int id, List<Long> startingItems, LongUnaryOperator operation, int mod, int ifTrue, int ifFalse) {

  @Override
  public int hashCode() {
    return id;
  }
}
