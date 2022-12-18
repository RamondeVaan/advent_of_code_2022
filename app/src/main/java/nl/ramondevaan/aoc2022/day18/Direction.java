package nl.ramondevaan.aoc2022.day18;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Direction {
  X_NEGATIVE(-1, 0, 0),
  X_POSITIVE(1, 0, 0),
  Y_NEGATIVE(0, -1, 0),
  Y_POSITIVE(0, 1, 0),
  Z_NEGATIVE(0, 0, -1),
  Z_POSITIVE(0, 0, 1);

  public final int xOffset;
  public final int yOffset;
  public final int zOffset;
}
