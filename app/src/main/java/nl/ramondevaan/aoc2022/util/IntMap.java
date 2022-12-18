package nl.ramondevaan.aoc2022.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class IntMap {

    private final int[][] map;
    private final int rows;
    private final int columns;
    private final int size;
    private final List<Coordinate> keys;

    public IntMap(Stream<IntStream> stream) {
        this.map = stream.map(IntStream::toArray).toArray(int[][]::new);
        this.rows = this.map.length;
        this.columns = this.rows == 0 ? 0 : this.map[0].length;
        this.size = this.rows * this.columns;
        this.keys = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                this.keys.add(new Coordinate(row, column));
            }
        }
        if (Arrays.stream(this.map, 1, rows).anyMatch(row -> row.length != columns)) {
            throw new IllegalStateException();
        }
    }

    private IntMap(final int[][] map) {
        this.map = map;
        this.rows = map.length;
        this.columns = this.rows == 0 ? 0 : this.map[0].length;
        this.size = this.rows * this.columns;
        this.keys = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                this.keys.add(new Coordinate(row, column));
            }
        }
    }

    public Stream<Coordinate> keys() {
        return keys.stream();
    }

    public IntStream values() {
        return IntStream.range(0, rows).flatMap(row -> Arrays.stream(this.map[row], 0, columns));
    }

    public Stream<IntMapEntry> entries() {
        return this.keys.stream().map(coordinate -> new IntMapEntry(
                coordinate,
                this.map[coordinate.row()][coordinate.column()]
        ));
    }

    public boolean contains(Coordinate coordinate) {
        return coordinate.row() >= 0 && coordinate.row() < rows
                && coordinate.column() >= 0 && coordinate.column() < columns;
    }

    public int rows() {
        return rows;
    }

    public int columns() {
        return columns;
    }

    public int size() {
        return size;
    }

    public int valueAt(int row, int column) {
        return map[row][column];
    }

    public int valueAt(Coordinate coordinate) {
        return map[coordinate.row()][coordinate.column()];
    }

    public IntMapEntry withValueAt(Coordinate coordinate) {
        return new IntMapEntry(coordinate, map[coordinate.row()][coordinate.column()]);
    }

    public void copyInto(int row, int[] destination) {
        System.arraycopy(this.map[row], 0, destination, 0, columns);
    }

    public void copyInto(final int row, final int[] destination, final int offset) {
        System.arraycopy(this.map[row], 0, destination, offset, columns);
    }

    public static Builder builder(int rows, int columns) {
        return new Builder(rows, columns);
    }

    public static class Builder {
        int[][] values;

        public Builder(int rows, int columns) {
            this.values = new int[rows][columns];
        }

        public Builder set(int row, int column, int value) {
            values[row][column] = value;
            return this;
        }

        public int get(final int row, final int column) {
            return values[row][column];
        }

        public Builder fill(final int value) {
            for (final int[] ints : values) {
                Arrays.fill(ints, value);
            }
            return this;
        }

        public IntMap build() {
            final var ret = new IntMap(values);
            this.values = null;
            return ret;
        }
    }
}
