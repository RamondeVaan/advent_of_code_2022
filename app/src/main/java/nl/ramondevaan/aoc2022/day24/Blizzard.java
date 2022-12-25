package nl.ramondevaan.aoc2022.day24;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2022.util.IntMap;

@Getter
@RequiredArgsConstructor
public enum Blizzard {
  UP(2, -1, 0) {
    public void setNext(final IntMap current, final IntMap.Builder next, int row, int column) {
      for (int nextRow = row - 1; nextRow >= 0 ; nextRow--) {
        if (current.valueAt(nextRow, column) != 1) {
          next.flag(nextRow, column, flag);
          return;
        }
      }
      for (int nextRow = next.rows - 1; nextRow > row ; nextRow--) {
        if (current.valueAt(nextRow, column) != 1) {
          next.flag(nextRow, column, flag);
          return;
        }
      }

      throw new IllegalArgumentException();
    }
  },
  LEFT(4, 0, -1) {

    public void setNext(final IntMap current, final IntMap.Builder next, int row, int column) {
      for (int nextColumn = column - 1; nextColumn >= 0 ; nextColumn--) {
        if (current.valueAt(row, nextColumn) != 1) {
          next.flag(row, nextColumn, flag);
          return;
        }
      }
      for (int nextColumn = next.columns - 1; nextColumn > column ; nextColumn--) {
        if (current.valueAt(row, nextColumn) != 1) {
          next.flag(row, nextColumn, flag);
          return;
        }
      }

      throw new IllegalArgumentException();
    }
  },
  RIGHT(8, 0, 1) {

    public void setNext(final IntMap current, final IntMap.Builder next, int row, int column) {
      for (int nextColumn = column + 1; nextColumn < next.columns ; nextColumn++) {
        if (current.valueAt(row, nextColumn) != 1) {
          next.flag(row, nextColumn, flag);
          return;
        }
      }
      for (int nextColumn = 0; nextColumn < column ; nextColumn++) {
        if (current.valueAt(row, nextColumn) != 1) {
          next.flag(row, nextColumn, flag);
          return;
        }
      }

      throw new IllegalArgumentException();
    }
  },
  DOWN(16, 1, 0) {

    public void setNext(final IntMap current, final IntMap.Builder next, int row, int column) {
      for (int nextRow = row + 1; nextRow < next.rows ; nextRow++) {
        if (current.valueAt(nextRow, column) != 1) {
          next.flag(nextRow, column, flag);
          return;
        }
      }
      for (int nextRow = 0; nextRow < row ; nextRow++) {
        if (current.valueAt(nextRow, column) != 1) {
          next.flag(nextRow, column, flag);
          return;
        }
      }

      throw new IllegalArgumentException();
    }
  };

  public final int flag;
  public final int rowOffset;
  public final int columnOffset;

  protected abstract void setNext(final IntMap current, final IntMap.Builder next, int row, int column);

}
