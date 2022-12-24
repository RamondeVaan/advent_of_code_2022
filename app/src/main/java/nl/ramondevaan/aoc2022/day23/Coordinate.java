package nl.ramondevaan.aoc2022.day23;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class Coordinate {

  public final short row;
  public final short column;

  @Override
  public int hashCode() {
    return (row << 16) | column;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final Coordinate that = (Coordinate) o;
    return row == that.row && column == that.column;
  }

  public Coordinate neighbor(final Direction direction) {
    return new Coordinate((short) (row + direction.rowOffset), (short) (column + direction.columnOffset));
  }
}
