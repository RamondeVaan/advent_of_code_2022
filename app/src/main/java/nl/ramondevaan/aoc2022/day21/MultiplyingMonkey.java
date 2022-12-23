package nl.ramondevaan.aoc2022.day21;

public class MultiplyingMonkey extends CommutativeComputingMonkey {

  public MultiplyingMonkey(final String name, final Monkey leftMonkey, final Monkey rightMonkey) {
    super(name, leftMonkey, rightMonkey);
  }

  @Override
  protected long compute(final long left, final long right) {
    return left * right;
  }

  @Override
  protected long inverse(final long target, final long value) {
    return target / value;
  }
}
