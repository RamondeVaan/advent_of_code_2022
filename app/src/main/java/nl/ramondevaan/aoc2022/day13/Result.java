package nl.ramondevaan.aoc2022.day13;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Result {
  VALID(-1),
  CONTINUE(0),

  INVALID(1);

  private final int value;
}
