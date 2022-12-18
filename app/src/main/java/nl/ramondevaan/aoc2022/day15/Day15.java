package nl.ramondevaan.aoc2022.day15;

import nl.ramondevaan.aoc2022.util.Position;

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

    final var intersections = getIntersections(min, max, min, max);
    final var set = new HashSet<Integer>();

    for (final var intersection : intersections) {
      set.add(intersection.y);
    }

    for (final var y : set) {
      final var emptyRanges = findEmptyRanges(y, min, max);
      if (totalCount(emptyRanges) == max) {
        final var lowestTo = emptyRanges.stream().mapToInt(Range::to).min().orElseThrow() + 1;
        if (lowestTo > max) {
          return y;
        }
        return (long) lowestTo * 4_000_000 + y;
      }
    }

    throw new IllegalArgumentException();
  }

  @SuppressWarnings("SameParameterValue")
  private List<Position> getIntersections(final int xMin, final int xMax, final int yMin, final int yMax) {
    final var ascendingLineSegments = getAscendingLineSegments();
    final var descendingLineSegments = getDescendingLineSegments();

    final var intersections = new ArrayList<Position>(ascendingLineSegments.length * descendingLineSegments.length);

    for (final var ascendingLineSegment : ascendingLineSegments) {
      for (final var descendingLineSegment : descendingLineSegments) {
        final var intersection = ascendingLineSegment.getIntersection(descendingLineSegment);
        if (intersection != null && xMin <= intersection.x && intersection.x <= xMax &&
            yMin <= intersection.y && intersection.y <= yMax) {
          intersections.add(intersection);
        }
      }
    }

    for (final var ascendingLineSegment : ascendingLineSegments) {
      intersections.addAll(ascendingLineSegment.getBoxIntersections(xMin, xMax, yMin, yMax));
    }

    for (final var descendingLineSegment : descendingLineSegments) {
      intersections.addAll(descendingLineSegment.getBoxIntersections(xMin, xMax, yMin, yMax));
    }

    return intersections;
  }

  private AscendingLineSegment[] getAscendingLineSegments() {
    final var lineSegments = new AscendingLineSegment[sensors.size() * 2];

    int count = 0;
    for (final var sensor : sensors) {
      lineSegments[count++] = new AscendingLineSegment(sensor.x - sensor.range - 1, sensor.y, sensor.range + 2);
      lineSegments[count++] = new AscendingLineSegment(sensor.x, sensor.y - sensor.range - 1, sensor.range + 2);
    }

    return lineSegments;
  }

  private DescendingLineSegment[] getDescendingLineSegments() {
    final var lineSegments = new DescendingLineSegment[sensors.size() * 2];

    int count = 0;
    for (final var sensor : sensors) {
      lineSegments[count++] = new DescendingLineSegment(sensor.x - sensor.range - 1, sensor.y, sensor.range + 2);
      lineSegments[count++] = new DescendingLineSegment(sensor.x, sensor.y + sensor.range + 1, sensor.range + 2);
    }

    return lineSegments;
  }

  private List<Range> findEmptyRanges(final int y, final int min, final int max) {
    final var emptyRanges = new ArrayList<Range>(sensors.size());

    for (final var sensor : sensors) {
      final var distanceToY = Math.abs(y - sensor.y);
      if (distanceToY < sensor.range) {
        final var overlapOffset = sensor.range - distanceToY;
        final var from = Math.max(sensor.x - overlapOffset, min);
        final var to = Math.min(sensor.x + overlapOffset, max);
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
      if (sensor.y == Y) {
        sensorOrBeaconPositions.add(sensor.x);
      }
      if (sensor.beaconY == Y) {
        sensorOrBeaconPositions.add(sensor.beaconX);
      }
    }

    return sensorOrBeaconPositions.size();
  }
}
