package nl.ramondevaan.aoc2022.day22;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2022.util.IntMap;

import java.util.EnumMap;

@RequiredArgsConstructor
public class CubePathHandler implements PathHandler {

  private final int faceSize;
  private final int faceSizeMinusOne;
  private final Face[][] faces;
  private final EnumMap<Face, Integer> faceRotation;
  private final IntMap toCubeRow;
  private final IntMap toCubeColumn;
  private final EnumMap<Face, IntMap> toRowByCube;
  private final EnumMap<Face, IntMap> toColumnByCube;

  @Override
  public MapPosition next(final int row, final int column, final Direction direction) {
    final var cubePosition = getCubePosition(row, column, direction);
    final var nextCubePosition = next(cubePosition);
    return getMapPosition(nextCubePosition);
  }

  private CubePosition next(final CubePosition position) {
    final var nextRow = position.row + position.direction.rowOffset;
    final var nextColumn = position.column + position.direction.columnOffset;

    if (0 > nextRow || nextRow >= faceSize || 0 > nextColumn || nextColumn >= faceSize) {
      final var nextFaceDirection = position.face.neighbor(position.direction);
      final var nextFace = nextFaceDirection.face;
      final var nextDirection = nextFaceDirection.direction;
      return switch (position.direction) {
        case LEFT -> switch (nextDirection) {
          case LEFT -> new CubePosition(nextFace, nextRow, faceSizeMinusOne, nextDirection);
          case RIGHT -> new CubePosition(nextFace, faceSizeMinusOne - nextRow, 0, nextDirection);
          case DOWN -> new CubePosition(nextFace, 0, nextRow, nextDirection);
          case UP -> new CubePosition(nextFace, faceSizeMinusOne, faceSizeMinusOne - nextRow, nextDirection);
        };
        case RIGHT -> switch (nextDirection) {
          case LEFT -> new CubePosition(nextFace, faceSizeMinusOne - nextRow, faceSizeMinusOne, nextDirection);
          case RIGHT -> new CubePosition(nextFace, nextRow, 0, nextDirection);
          case DOWN -> new CubePosition(nextFace, 0, faceSizeMinusOne - nextRow, nextDirection);
          case UP -> new CubePosition(nextFace, faceSizeMinusOne, nextRow, nextDirection);
        };
        case DOWN -> switch (nextDirection) {
          case LEFT -> new CubePosition(nextFace, nextColumn, faceSizeMinusOne, nextDirection);
          case RIGHT -> new CubePosition(nextFace, faceSizeMinusOne - nextColumn, 0, nextDirection);
          case DOWN -> new CubePosition(nextFace, 0, nextColumn, nextDirection);
          case UP -> new CubePosition(nextFace, faceSizeMinusOne, faceSizeMinusOne - nextColumn, nextDirection);
        };
        case UP -> switch (nextDirection) {
          case LEFT -> new CubePosition(nextFace, faceSizeMinusOne - nextColumn, faceSizeMinusOne, nextDirection);
          case RIGHT -> new CubePosition(nextFace, nextColumn, 0, nextDirection);
          case DOWN -> new CubePosition(nextFace, 0, faceSizeMinusOne - nextColumn, nextDirection);
          case UP -> new CubePosition(nextFace, faceSizeMinusOne, nextColumn, nextDirection);
        };
      };
    }

    return new CubePosition(position.face, nextRow, nextColumn, position.direction);
  }

  private CubePosition getCubePosition(final int row, final int column, final Direction direction) {
    final var face = faces[row][column];

    return new CubePosition(face,
        toCubeRow.valueAt(row, column),
        toCubeColumn.valueAt(row, column),
        direction.rotate(faceRotation.get(face)));
  }

  private MapPosition getMapPosition(final CubePosition cubePosition) {
    return new MapPosition(
        toRowByCube.get(cubePosition.face).valueAt(cubePosition.row, cubePosition.column),
        toColumnByCube.get(cubePosition.face).valueAt(cubePosition.row, cubePosition.column),
        cubePosition.direction.rotate(-faceRotation.get(cubePosition.face)));
  }

  public static Builder builder(final int rows, final int columns) {
    return new Builder(rows, columns);
  }

  public static class Builder {

    public final int rows;
    public final int columns;
    public final int faceSize;
    public final int faceSizeMinusOne;
    private final IntMap absoluteRows;
    private final IntMap absoluteColumns;
    private final IntMap cubeRows;
    private final IntMap cubeColumns;
    private IntMap.Builder toCubeRow;
    private IntMap.Builder toCubeColumn;
    private Face[][] faces;
    private Face[][] relativeFaces;
    private EnumMap<Face, Integer> faceRotation;
    private EnumMap<Face, IntMap> toRowByCube;
    private EnumMap<Face, IntMap> toColumnByCube;

    public Builder(final int rows, final int columns) {
      this.rows = rows;
      this.columns = columns;
      this.faceSize = getFaceSize(rows, columns);
      this.faceSizeMinusOne = faceSize - 1;
      this.faces = new Face[rows][columns];
      this.relativeFaces = new Face[rows / faceSize][columns / faceSize];
      faceRotation = new EnumMap<>(Face.class);
      toRowByCube = new EnumMap<>(Face.class);
      toColumnByCube = new EnumMap<>(Face.class);
      toCubeRow = IntMap.builder(rows, columns);
      toCubeColumn = IntMap.builder(rows, columns);
      this.absoluteRows = getRowMap(rows, columns);
      this.absoluteColumns = getColumnMap(rows, columns);
      this.cubeRows = getRowMap(faceSize, faceSize);
      this.cubeColumns = getColumnMap(faceSize, faceSize);
    }

    public Builder setFace(final int relativeRow, final int relativeColumn, final Face face, final int rotations) {
      relativeFaces[relativeRow][relativeColumn] = face;
      fillAbsoluteFaces(relativeRow, relativeColumn, face);
      faceRotation.put(face, rotations);

      final var absoluteRow = relativeRow * faceSize;
      final var absoluteColumn = relativeColumn * faceSize;
      toCubeRow.copyFrom(cubeRows.rotate(-rotations), 0, absoluteRow, 0, absoluteColumn, faceSize, faceSize);
      toCubeColumn.copyFrom(cubeColumns.rotate(-rotations), 0, absoluteRow, 0, absoluteColumn, faceSize, faceSize);

      toRowByCube.put(face, absoluteRows.part(absoluteRow, absoluteColumn, faceSize, faceSize).rotate(rotations));
      toColumnByCube.put(face, absoluteColumns.part(absoluteRow, absoluteColumn, faceSize, faceSize).rotate(rotations));

      return this;
    }

    private void fillAbsoluteFaces(final int relativeRow, final int relativeColumn, final Face face) {
      final var startRow = relativeRow * faceSize;
      final var startColumn = relativeColumn * faceSize;
      final var targetRow = startRow + faceSize;
      final var targetColumn = startColumn + faceSize;

      for (int row = startRow; row < targetRow; row++) {
        for (int column = startColumn; column < targetColumn; column++) {
          faces[row][column] = face;
        }
      }
    }

    public int getRotation(final Face face) {
      return faceRotation.get(face);
    }

    public boolean hasFace(final int relativeRow, final int relativeColumn, final Face face) {
      return 0 > relativeRow || relativeRow >= relativeFaces.length ||
          0 > relativeColumn || relativeColumn >= relativeFaces[relativeRow].length ||
          faceRotation.containsKey(face);
    }

    public CubePathHandler build() {
      final var ret = new CubePathHandler(faceSize, faceSizeMinusOne, faces, faceRotation, toCubeRow.build(),
          toCubeColumn.build(), toRowByCube, toColumnByCube);

      this.faces = null;
      this.relativeFaces = null;
      this.faceRotation = null;
      this.toCubeRow = null;
      this.toCubeColumn = null;
      this.toRowByCube = null;
      this.toColumnByCube = null;

      return ret;
    }

    private static int getFaceSize(final int rows, final int columns) {
      if (rows * 5 == columns * 2) {
        return columns / 2;
      } else if (rows * 2 == columns * 5) {
        return rows / 2;
      } else if (rows > columns) {
        return rows / 4;
      }

      return columns / 4;
    }

    private static IntMap getRowMap(final int rows, final int columns) {
      final var builder = IntMap.builder(rows, columns);

      for (int row = 0; row < rows; row++) {
        for (int column = 0; column < columns; column++) {
          builder.set(row, column, row);
        }
      }

      return builder.build();
    }

    private static IntMap getColumnMap(final int rows, final int columns) {
      final var builder = IntMap.builder(rows, columns);

      for (int row = 0; row < rows; row++) {
        for (int column = 0; column < columns; column++) {
          builder.set(row, column, column);
        }
      }

      return builder.build();
    }
  }
}
