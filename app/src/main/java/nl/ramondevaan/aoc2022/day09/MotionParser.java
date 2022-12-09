package nl.ramondevaan.aoc2022.day09;

import nl.ramondevaan.aoc2022.util.Parser;

public class MotionParser implements Parser<String, Motion> {
  @Override
  public Motion parse(String toParse) {
    final var direction = switch (toParse.charAt(0)) {
      case 'R' -> Direction.RIGHT;
      case 'U' -> Direction.UP;
      case 'L' -> Direction.LEFT;
      case 'D' -> Direction.DOWN;
      default -> throw new IllegalArgumentException();
    };
    final var distance = Integer.parseInt(toParse.substring(2));

    return new Motion(direction, distance);
  }
}
