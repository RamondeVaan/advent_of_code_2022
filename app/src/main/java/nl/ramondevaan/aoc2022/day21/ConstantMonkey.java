package nl.ramondevaan.aoc2022.day21;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConstantMonkey implements Monkey {

  public final String name;
  public final long number;

  @Override
  public String name() {
    return name;
  }

  @Override
  public long yell() {
    return number;
  }

  @Override
  public boolean contains(final Monkey monkey) {
    return this == monkey;
  }

  @Override
  public long inverse(final long target, final ConstantMonkey monkey) {
    if (this == monkey) {
      return target;
    }

    throw new IllegalArgumentException();
  }
}
