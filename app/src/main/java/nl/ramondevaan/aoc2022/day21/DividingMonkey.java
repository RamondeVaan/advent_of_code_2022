package nl.ramondevaan.aoc2022.day21;

public class DividingMonkey extends AntiCommutativeComputingMonkey {

  public DividingMonkey(final String name, final Monkey leftMonkey, final Monkey rightMonkey) {
    super(name, leftMonkey, rightMonkey);
  }

  @Override
  protected long compute(final long left, final long right) {
    return left / right;
  }

  @Override
  protected long inverseLeft(final long target, final long right) {
    return target * right;
  }

  @Override
  protected long inverseRight(final long target, final long left) {
    throw new UnsupportedOperationException();
  }
}
