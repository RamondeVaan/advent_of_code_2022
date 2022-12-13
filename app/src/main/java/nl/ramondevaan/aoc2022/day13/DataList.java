package nl.ramondevaan.aoc2022.day13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataList implements Data {

  private final List<Data> values;

  private DataList(final List<Data> values) {
    this.values = values;
  }

  public DataList(final Data value) {
    this(List.of(value));
  }

  public int size() {
    return values.size();
  }

  public Data get(final int i) {
    return values.get(i);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private List<Data> data;

    public Builder() {
      this.data = new ArrayList<>();
    }

    public Builder add(final Data data) {
      this.data.add(data);
      return this;
    }

    public DataList build() {
      final var ret = new DataList(Collections.unmodifiableList(data));
      this.data = null;
      return ret;
    }
  }
}
