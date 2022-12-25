package nl.ramondevaan.aoc2022.day24;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Move {
  UP(-1, 0),
  LEFT(0, -1),
  RIGHT(0, 1),
  DOWN(1, 0),
  WAIT(0,0);

  public final int rowOffset;
  public final int columnOffset;
  }