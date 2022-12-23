package nl.ramondevaan.aoc2022.day21;

public abstract class CommutativeComputingMonkey extends ComputingMonkey {
  public CommutativeComputingMonkey(final String name, final Monkey leftMonkey, final Monkey rightMonkey) {
    super(name, leftMonkey, rightMonkey);
  }

  @Override
  public long inverse(final long target, final ConstantMonkey monkey) {
    if (leftMonkey.contains(monkey)) {
      return leftMonkey.inverse(inverse(target, rightMonkey.yell()), monkey);
    }

    return rightMonkey.inverse(inverse(target, leftMonkey.yell()), monkey);
  }

  protected abstract long inverse(long target, long value);
}
