package nl.ramondevaan.aoc2022.day23;

import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public class Consideration {
  public final Direction proposal;
  public final Set<Direction> checkDirections;
}
