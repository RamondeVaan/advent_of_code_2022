package nl.ramondevaan.aoc2022.day10;

import nl.ramondevaan.aoc2022.util.ImmutableIntArray;
import nl.ramondevaan.aoc2022.util.LetterParser;

import java.util.List;

public class Day10 {

  private final LettersParser lettersParser;
  private final ImmutableIntArray xValuesByCycle;

  public Day10(final List<String> lines) {
    final var letterParser = new LetterParser();
    final var letters = letterParser.parse(Day10.class.getResource("/day_10_letters.txt"));
    this.lettersParser = new LettersParser(letters);

    final var parser = new InstructionParser();
    final var instructions = lines.stream().map(parser::parse).toList();

    final var xValuesByCycle = new int[instructions.size() * 2 + 1];
    var lastX = xValuesByCycle[0] = xValuesByCycle[1] = 1;
    var cycleIndex = 2;
    for (final var instruction : instructions) {
      xValuesByCycle[cycleIndex++] = lastX;
      if (instruction instanceof AddX addX) {
        xValuesByCycle[cycleIndex++] = lastX += addX.value();
      }
    }
    this.xValuesByCycle = ImmutableIntArray.of(xValuesByCycle, cycleIndex - 1);
  }

  public long solve1() {
    var interestingSignalStrengthSum = 0;

    for (int cycle = 20; cycle <= 220; cycle += 40) {
      interestingSignalStrengthSum += xValuesByCycle.get(cycle) * cycle;
    }

    return interestingSignalStrengthSum;
  }

  public String solve2() {
    final int rows = 6;
    final int columns = 40;
    boolean[][] screen = new boolean[rows][columns];
    var cycle = 1;

    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        screen[row][column] = Math.abs(column - xValuesByCycle.get(cycle++)) <= 1;
      }
    }

    return lettersParser.parse(screen);
  }
}
