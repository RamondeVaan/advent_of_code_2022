package nl.ramondevaan.aoc2022.day16;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Valve {
  public final int id;
  public final String name;
  public final int flowRate;
  @Override
  public int hashCode() {
    return id;
  }
}
