package nl.ramondevaan.aoc2022.day21;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class ComputingMonkey implements Monkey {
  public final String name;

  public final Monkey leftMonkey;
  public final Monkey rightMonkey;

  @Override
  public String name() {
    return name;
  }

  @Override
  public final long yell() {
    return compute(leftMonkey.yell(), rightMonkey.yell());
  }

  protected abstract long compute(final long left, final long right);

  @Override
  public boolean contains(final Monkey monkey) {
    return leftMonkey.contains(monkey) || rightMonkey.contains(monkey);
  }
}
