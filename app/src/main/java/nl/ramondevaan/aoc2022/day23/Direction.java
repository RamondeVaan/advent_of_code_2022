package nl.ramondevaan.aoc2022.day23;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Direction {
  NORTH(-1, 0),
  EAST(0, 1),
  SOUTH(1, 0),
  WEST(0, -1),
  NORTH_EAST(-1, 1),
  SOUTH_EAST(1, 1),
  SOUTH_WEST(1, -1),
  NORTH_WEST(-1, -1);

  public final int rowOffset;
  public final int columnOffset;
}
