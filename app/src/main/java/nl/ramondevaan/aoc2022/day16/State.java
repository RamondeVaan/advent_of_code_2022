package nl.ramondevaan.aoc2022.day16;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class State {
  public final Valve position;
  public final int pressure;
  public final int used;
  public final int timeRemaining;
}
