package nl.ramondevaan.aoc2022.day12;

import nl.ramondevaan.aoc2022.util.Coordinate;
import nl.ramondevaan.aoc2022.util.IntMap;
import nl.ramondevaan.aoc2022.util.MutableIntMap;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Day12 {

  private final IntMap heightMap;
  private final Coordinate start;
  private final Coordinate end;

  public Day12(final List<String> lines) {
    final var parser = new HeightMapParser();
    final var heightMap = parser.parse(lines);
    this.heightMap = heightMap.map();
    this.start = heightMap.start();
    this.end = heightMap.end();
  }

  public long solve1() {
    return solve(coordinate -> Objects.equals(start, coordinate));
  }

  public long solve2() {
    return solve(coordinate -> heightMap.valueAt(coordinate) == 0);
  }

  private long solve(final Predicate<Coordinate> stoppingCondition) {
    final var distanceMap = new MutableIntMap(heightMap.rows(), heightMap.columns());
    distanceMap.fill(Integer.MAX_VALUE);
    final var queue = new ArrayDeque<Coordinate>(heightMap.size());
    distanceMap.setValueAt(end, 0);
    queue.add(end);

    Coordinate coordinate;
    while ((coordinate = queue.poll()) != null) {
      if (stoppingCondition.test(coordinate)) {
        return distanceMap.valueAt(coordinate);
      }
      final var height = heightMap.valueAt(coordinate);
      final var nextDistance = distanceMap.valueAt(coordinate) + 1;
      coordinate.directNeighbors().filter(heightMap::contains)
          .filter(c -> distanceMap.valueAt(c) == Integer.MAX_VALUE)
          .filter(c -> heightMap.valueAt(c) - height >= -1)
          .forEach(neighbor -> {
            distanceMap.setValueAt(neighbor, nextDistance);
            queue.add(neighbor);
          });
    }

    throw new IllegalArgumentException("End is not reachable");
  }
}
