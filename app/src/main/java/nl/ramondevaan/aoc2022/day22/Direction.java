package nl.ramondevaan.aoc2022.day22;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Direction {

  RIGHT(0, 1) {
    @Override
    public Direction rotateClockwise() {
      return DOWN;
    }

    @Override
    public Direction rotateCounterClockwise() {
      return UP;
    }

    @Override
    public Direction opposite() {
      return LEFT;
    }

    @Override
    public int getFirstRow(final int row, final int rows) {
      return row;
    }

    @Override
    public int getFirstColumn(final int column, final int columns) {
      return 0;
    }

    @Override
    public int rotationsTo(final Direction direction) {
      return switch (direction) {
        case UP -> -1;
        case RIGHT -> 0;
        case DOWN -> 1;
        case LEFT -> 2;
      };
    }
  },
  DOWN(1, 0) {
    @Override
    public Direction rotateClockwise() {
      return LEFT;
    }

    @Override
    public Direction rotateCounterClockwise() {
      return RIGHT;
    }

    @Override
    public Direction opposite() {
      return UP;
    }

    @Override
    public int getFirstRow(final int row, final int rows) {
      return 0;
    }

    @Override
    public int getFirstColumn(final int column, final int columns) {
      return column;
    }

    @Override
    public int rotationsTo(final Direction direction) {
      return switch (direction) {
        case UP -> 2;
        case RIGHT -> -1;
        case DOWN -> 0;
        case LEFT -> 1;
      };
    }
  },
  LEFT(0, -1) {
    @Override
    public Direction rotateClockwise() {
      return UP;
    }

    @Override
    public Direction rotateCounterClockwise() {
      return DOWN;
    }

    @Override
    public Direction opposite() {
      return RIGHT;
    }

    @Override
    public int getFirstRow(final int row, final int rows) {
      return row;
    }

    @Override
    public int getFirstColumn(final int column, final int columns) {
      return columns - 1;
    }

    @Override
    public int rotationsTo(final Direction direction) {
      return switch (direction) {
        case UP -> 1;
        case RIGHT -> 2;
        case DOWN -> -1;
        case LEFT -> 0;
      };
    }
  },
  UP(-1, 0) {
    @Override
    public Direction rotateClockwise() {
      return RIGHT;
    }

    @Override
    public Direction rotateCounterClockwise() {
      return LEFT;
    }

    @Override
    public Direction opposite() {
      return DOWN;
    }

    @Override
    public int getFirstRow(final int row, final int rows) {
      return rows - 1;
    }

    @Override
    public int getFirstColumn(final int column, final int columns) {
      return column;
    }

    @Override
    public int rotationsTo(final Direction direction) {
      return switch (direction) {
        case UP -> 0;
        case RIGHT -> 1;
        case DOWN -> 2;
        case LEFT -> -1;
      };
    }
  };

  public final int rowOffset;
  public final int columnOffset;

  public abstract Direction rotateClockwise();

  public abstract Direction rotateCounterClockwise();

  public abstract Direction opposite();

  public abstract int getFirstRow(final int row, final int rows);
  public abstract int getFirstColumn(final int column, final int rows);
  public abstract int rotationsTo(final Direction direction);

  public Direction rotate(final int rotations) {
    int clockwiseRotations = Math.floorMod(rotations, 4);

    return switch (clockwiseRotations) {
      case 0 -> this;
      case 1 -> rotateClockwise();
      case 2 -> opposite();
      case 3 -> rotateCounterClockwise();
      default -> throw new IllegalStateException();
    };
  }
}
