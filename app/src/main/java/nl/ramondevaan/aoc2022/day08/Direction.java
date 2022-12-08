package nl.ramondevaan.aoc2022.day08;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Direction {
  UP(-1, 0),
  LEFT(0, -1),
  RIGHT(0, 1),
  DOWN(1, 0);

  private final int rowOffset;
  private final int columnOffset;

}
