package nl.ramondevaan.aoc2022.day24;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2022.util.IntMap;

@RequiredArgsConstructor
public class Basin {
  public final IntMap map;
  public final int startRow;
  public final int startColumn;
  public final int endRow;
  public final int endColumn;
}
