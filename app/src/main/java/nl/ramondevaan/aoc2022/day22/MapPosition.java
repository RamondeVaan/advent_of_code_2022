package nl.ramondevaan.aoc2022.day22;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class MapPosition {
  public final int row;
  public final int column;
  public final Direction direction;
}
