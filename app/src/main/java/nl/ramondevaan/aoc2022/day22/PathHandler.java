package nl.ramondevaan.aoc2022.day22;

public interface PathHandler {

  MapPosition next(final int row, final int column, final Direction direction);
}
