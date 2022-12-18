package nl.ramondevaan.aoc2022.day17;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Jet {
  LEFT(-1), RIGHT(1);

  public final int columnOffset;
}
