package nl.ramondevaan.aoc2022.day21;

public class SubtractingMonkey extends AntiCommutativeComputingMonkey {

  public SubtractingMonkey(final String name, final Monkey leftMonkey, final Monkey rightMonkey) {
    super(name, leftMonkey, rightMonkey);
  }

  @Override
  protected long inverseLeft(final long target, final long right) {
    return target + right;
  }

  @Override
  protected long inverseRight(final long target, final long left) {
    return -target + left;
  }

  @Override
  protected long compute(final long left, final long right) {
    return left - right;
  }
}
