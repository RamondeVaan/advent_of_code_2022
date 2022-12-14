package nl.ramondevaan.aoc2022.day14;

import nl.ramondevaan.aoc2022.util.Coordinate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Path {

  private final List<Coordinate> points;

  private Path(final List<Coordinate> points) {
    this.points = points;
  }

  public static Builder builder() {
    return new Builder();
  }

  public List<Coordinate> getPoints() {
    return points;
  }

  public static class Builder {
    private List<Coordinate> points;

    public Builder() {
      this.points = new ArrayList<>();
    }

    public Builder addPoint(final int row, final int column) {
      this.points.add(new Coordinate(row, column));
      return this;
    }

    public Path build() {
      final var ret = new Path(Collections.unmodifiableList(points));
      this.points = null;
      return ret;
    }
  }
}
