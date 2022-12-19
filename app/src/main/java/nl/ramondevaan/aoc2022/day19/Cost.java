package nl.ramondevaan.aoc2022.day19;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
@Builder
public class Cost {

  public final int ore;
  public final int clay;
  public final int obsidian;
}
