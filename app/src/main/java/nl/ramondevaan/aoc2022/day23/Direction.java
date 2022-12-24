package nl.ramondevaan.aoc2022.day23;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Direction {
  NORTH(-2000),
  EAST(1),
  SOUTH(2000),
  WEST(-1),
  NORTH_EAST(-1999),
  SOUTH_EAST(2001),
  SOUTH_WEST(1999),
  NORTH_WEST(-2001);

  public final int offset;
}
