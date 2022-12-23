package nl.ramondevaan.aoc2022.day21;

public abstract class AntiCommutativeComputingMonkey extends ComputingMonkey {
  public AntiCommutativeComputingMonkey(final String name, final Monkey leftMonkey, final Monkey rightMonkey) {
    super(name, leftMonkey, rightMonkey);
  }

  @Override
  public long inverse(final long target, final ConstantMonkey monkey) {
    if (leftMonkey.contains(monkey)) {
      return leftMonkey.inverse(inverseLeft(target, rightMonkey.yell()), monkey);
    }

    return rightMonkey.inverse(inverseRight(target, leftMonkey.yell()), monkey);
  }

  protected abstract long inverseLeft(final long target, final long right);
  protected abstract long inverseRight(final long target, final long left);
}
