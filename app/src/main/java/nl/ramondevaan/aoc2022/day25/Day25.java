package nl.ramondevaan.aoc2022.day25;

import java.util.List;

public class Day25 {

  private final List<String> fuelRequirements;

  public Day25(final List<String> lines) {
    this.fuelRequirements = List.copyOf(lines);
  }

  public String solve1() {
    final var sum = fuelRequirements.stream().mapToLong(this::toDecimal).sum();
    return toSnafu(sum);
  }

  private long toDecimal(final String snafu) {
    final var chars = snafu.toCharArray();

    long sum = 0, pow = 1;

    for (int i = chars.length - 1; i >= 0; i--, pow *= 5) {
      sum += pow * switch (chars[i]) {
        case '=' -> -2;
        case '-' -> -1;
        case '0' -> 0;
        case '1' -> 1;
        case '2' -> 2;
        default -> throw new IllegalArgumentException();
      };
    }

    return sum;
  }

  private String toSnafu(long decimal) {
    final var builder = new StringBuilder();

    do {
      final var val = (int) (decimal % 5);
      builder.append(switch (val) {
        case 0 -> '0';
        case 1 -> '1';
        case 2 -> '2';
        case 3 -> '=';
        case 4 -> '-';
        default -> throw new IllegalStateException();
      });
    } while ((decimal = (decimal + 2) / 5) != 0);

    return builder.reverse().toString();
  }
}
