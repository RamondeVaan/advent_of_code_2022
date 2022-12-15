package nl.ramondevaan.aoc2022.day15;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class Day15 {

  private final static int Y = 2_000_000;
  private final List<Sensor> sensors;

  public Day15(final List<String> lines) {
    final var parser = new SensorParser();
    this.sensors = lines.stream().map(parser::parse).toList();
  }

  public long solve1() {
    final var emptyCount = totalCount(findEmptyRanges(Y, Integer.MIN_VALUE, Integer.MAX_VALUE));
    final var sensorsOrBeaconsCount = countSensorsOrBeaconsAt();
    return emptyCount - sensorsOrBeaconsCount;
  }

  public long solve2() {
    final var min = 0;
    final var max = 4_000_000;

    for (int y = 0; y < max; y++) {
      final var emptyRanges = findEmptyRanges(y, min, max);
      if (totalCount(emptyRanges) == max) {
        final var lowestTo = emptyRanges.stream().mapToInt(Range::to).min().orElseThrow() + 1;
        if (lowestTo > max) {
          return y;
        }
        return (long) lowestTo * max + y;
      }
    }

    throw new IllegalArgumentException();
  }

  private List<Range> findEmptyRanges(final int y, final int min, final int max) {
    final var emptyRanges = new ArrayList<Range>(sensors.size());

    for (final var sensor : sensors) {
      final var sensorRange = Math.abs(sensor.beaconX() - sensor.x()) + Math.abs(sensor.beaconY() - sensor.y());
      final var distanceToY = Math.abs(y - sensor.y());
      if (distanceToY < sensorRange) {
        final var overlapOffset = sensorRange - distanceToY;
        final var from = Math.max(sensor.x() - overlapOffset, min);
        final var to = Math.min(sensor.x() + overlapOffset, max);
        if (from <= to) {
          addAndCompact(emptyRanges, new Range(from, to));
        }
      }
    }

    return Collections.unmodifiableList(emptyRanges);
  }

  private void addAndCompact(final List<Range> ranges, final Range newRange) {
    var checking = newRange;
    final var iterator = ranges.listIterator();

    while (iterator.hasNext()) {
      final var range = iterator.next();
      if (checking.from() <= range.to() && checking.to() >= range.from()) {
        iterator.remove();
        checking = new Range(Math.min(range.from(), checking.from()), Math.max(range.to(), checking.to()));
      }
    }

    ranges.add(checking);
  }

  private int totalCount(final List<Range> ranges) {
    var count = 0;

    for (final var range : ranges) {
      count += range.to() - range.from() + 1;
    }

    return count;
  }

  private int countSensorsOrBeaconsAt() {
    final var sensorOrBeaconPositions = new HashSet<Integer>();

    for (final var sensor : sensors) {
      if (sensor.y() == Y) {
        sensorOrBeaconPositions.add(sensor.x());
      }
      if (sensor.beaconY() == Y) {
        sensorOrBeaconPositions.add(sensor.beaconX());
      }
    }

    return sensorOrBeaconPositions.size();
  }
}
