package nl.ramondevaan.aoc2022.day18;

@FunctionalInterface
public interface PositionConsumer {

  void consume(int x, int y, int z);
}
