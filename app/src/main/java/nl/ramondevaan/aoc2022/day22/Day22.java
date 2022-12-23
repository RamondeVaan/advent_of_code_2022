package nl.ramondevaan.aoc2022.day22;

import nl.ramondevaan.aoc2022.util.BlankStringPartitioner;
import nl.ramondevaan.aoc2022.util.IntMap;

import java.util.List;

import static java.util.stream.IntStream.range;

public class Day22 {

  private final IntMap map;
  private final FlatPathHandler flatPathHandler;
  private final CubePathHandler cubePathHandler;
  private final List<Instruction> path;

  public Day22(final List<String> lines) {
    final var mapParser = new MapParser();
    final var cubePathHandlerParser = new CubePathHandlerParser();
    final var pathParser = new PathParser();
    final var partitioner = new BlankStringPartitioner();
    final var partitions = partitioner.partition(lines);

    this.map = mapParser.parse(partitions.get(0));
    this.flatPathHandler = new FlatPathHandler(map);
    this.cubePathHandler = cubePathHandlerParser.parse(map);
    this.path = pathParser.parse(partitions.get(1).get(0));
  }

  public long solve1() {
    return solve(flatPathHandler);
  }

  public long solve2() {
    return solve(cubePathHandler);
  }


  public long solve(final PathHandler pathHandler) {
    var row = 0;
    var column = range(0, map.columns()).filter(c -> map.valueAt(0, c) == 1).findFirst().orElseThrow();
    var facing = Direction.RIGHT;

    for (final var instruction : path) {
      if (instruction instanceof Forward forward) {
        for (int i = 0; i < forward.amount; i++) {
          final var next = pathHandler.next(row, column, facing);

          if (map.valueAt(next.row, next.column) == 2) {
            break;
          }

          row = next.row;
          column = next.column;
          facing = next.direction;
        }
      } else if (instruction instanceof RotateCounterClockwise) {
        facing = facing.rotateCounterClockwise();
      } else if (instruction instanceof RotateClockwise) {
        facing = facing.rotateClockwise();
      }
    }

    return score(row, column, facing);
  }

  private int score(final int row, final int column, final Direction facing) {
    final var facingScore = switch (facing) {
      case RIGHT -> 0;
      case DOWN -> 1;
      case LEFT -> 2;
      case UP -> 3;
    };
    return (row + 1) * 1_000 + 4 * (column + 1) + facingScore;
  }
}
