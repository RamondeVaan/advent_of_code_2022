package nl.ramondevaan.aoc2022.day18;

import java.util.List;

public class Day18 {

  private final static int EMPTY = 0;
  private final static int LAVA = 1;
  private final static int WATER = 2;
  private final Positions positions;
  private final int surfaceArea;
  private final IntMap3D map;

  public Day18(final List<String> lines) {
    final var parser = new PositionsParser();
    this.positions = parser.parse(lines);

    final var map = initializeMap(positions);
    this.surfaceArea = addWaterAndCountSurfaceArea(map);
    this.map = map.build();
  }

  public long solve1() {
    var count = 0;

    for (final var position : positions.positions) {
      for (final var direction : Direction.values()) {
        if (map.getOffset(position.x + direction.xOffset, position.y + direction.yOffset,
            position.z + direction.zOffset) != LAVA) {
          count++;
        }
      }
    }

    return count;
  }

  public long solve2() {
    return surfaceArea;
  }

  private static IntMap3D.Builder initializeMap(final Positions positions) {
    final var xSize = positions.xMax - positions.xMin + 3;
    final var ySize = positions.yMax - positions.yMin + 3;
    final var zSize = positions.zMax - positions.zMin + 3;

    final var map = IntMap3D.builder(xSize, ySize, zSize, positions.xMin - 1, positions.yMin - 1, positions.zMin - 1);

    positions.forEach((x, y, z) -> map.setOffset(x, y, z, LAVA));

    return map;
  }

  private static int addWaterAndCountSurfaceArea(final IntMap3D.Builder map) {
    final var positions = new int[map.size * 3];

    int written = 3, read = 0, count = 0;
    int x, y, z, nx, ny, nz;
    map.set(0, 0, 0, WATER);

    while (read < written) {
      x = positions[read++];
      y = positions[read++];
      z = positions[read++];

      for (final var direction : Direction.values()) {
        nx = x + direction.xOffset;
        ny = y + direction.yOffset;
        nz = z + direction.zOffset;

        if (map.isInRange(nx, ny, nz)) {
          switch (map.get(nx, ny, nz)) {
            case EMPTY -> {
              positions[written++] = nx;
              positions[written++] = ny;
              positions[written++] = nz;
              map.set(nx, ny, nz, WATER);
            }
            case LAVA -> count++;
          }
        }
      }
    }

    return count;
  }
}
