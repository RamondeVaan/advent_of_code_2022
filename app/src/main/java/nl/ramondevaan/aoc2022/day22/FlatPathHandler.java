package nl.ramondevaan.aoc2022.day22;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2022.util.IntMap;

@RequiredArgsConstructor
public class FlatPathHandler implements PathHandler {

  public final IntMap map;

  @Override
  public MapPosition next(final int row, final int column, final Direction direction) {
    var nextRow = row + direction.rowOffset;
    var nextColumn = column + direction.columnOffset;

    if (0 > nextRow || nextRow >= map.rows() ||
        0 > nextColumn || nextColumn >= map.columns() ||
        map.valueAt(nextRow, nextColumn) == 0) {
      nextRow = direction.getFirstRow(nextRow, map.rows());
      nextColumn = direction.getFirstColumn(nextColumn, map.columns());

      while (map.valueAt(nextRow, nextColumn) == 0) {
        nextRow += direction.rowOffset;
        nextColumn += direction.columnOffset;
      }
    }

    return new MapPosition(nextRow, nextColumn, direction);
  }
}
