package nl.ramondevaan.aoc2022.day22;

import nl.ramondevaan.aoc2022.util.IntMap;
import nl.ramondevaan.aoc2022.util.Parser;

public class CubePathHandlerParser implements Parser<IntMap, CubePathHandler> {
  @Override
  public CubePathHandler parse(final IntMap toParse) {
    final var builder = CubePathHandler.builder(toParse.rows(), toParse.columns());

    int column = 0;
    for (; column < toParse.columns(); column += builder.faceSize) {
      if (toParse.valueAt(0, column) != 0) {
        builder.setFace(0, column /= builder.faceSize, Face.TOP, 0);
        break;
      }
    }

    findFaces(toParse, 0, column, Face.TOP, builder);

    return builder.build();
  }

  private void findFaces(final IntMap toParse, final int row, final int column, final Face current,
                         final CubePathHandler.Builder builder) {
    for (final var direction : Direction.values()) {
      final Direction mappedDirection = direction.rotate(builder.getRotation(current));
      final int newRow = row + direction.rowOffset;
      final int newColumn = column + direction.columnOffset;
      final var newFace = current.neighbor(mappedDirection);

      if (builder.hasFace(newRow, newColumn, newFace.face) ||
          toParse.valueAt(newRow * builder.faceSize, newColumn * builder.faceSize) == 0) {
        continue;
      }

      final var newRotations = direction.rotationsTo(newFace.direction);
      builder.setFace(newRow, newColumn, newFace.face, newRotations);

      findFaces(toParse, newRow, newColumn, newFace.face, builder);
    }
  }
}
