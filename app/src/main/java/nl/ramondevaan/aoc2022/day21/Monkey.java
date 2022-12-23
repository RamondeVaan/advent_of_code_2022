package nl.ramondevaan.aoc2022.day21;

public interface Monkey {

  String name();

  long yell();

  boolean contains(final Monkey monkey);

  long inverse(final long target, final ConstantMonkey monkey);
}
