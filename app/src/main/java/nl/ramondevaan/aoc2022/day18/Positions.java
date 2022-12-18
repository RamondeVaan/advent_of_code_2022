package nl.ramondevaan.aoc2022.day18;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Positions {
  public final List<Position> positions;
  public final byte xMin;
  public final byte xMax;
  public final byte yMin;
  public final byte yMax;
  public final byte zMin;
  public final byte zMax;

  public void forEach(final PositionConsumer consumer) {
    for (final Position position : positions) {
      consumer.consume(position.x, position.y, position.z);
    }
  }
}
