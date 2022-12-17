package nl.ramondevaan.aoc2022.day16;

import java.util.*;

public class Valves implements Iterable<Valve> {

  private final Valve startValve;
  private final Valve[] valves;
  private final Valve[] positiveFlowRateValves;
  private final Valve[][] tunnels;

  private Valves(Valve startValve, Valve[] valves, Valve[] positiveFlowRateValves, Valve[][] tunnels) {
    this.startValve = startValve;
    this.valves = valves;
    this.positiveFlowRateValves = positiveFlowRateValves;
    this.tunnels = tunnels;
  }

  public static Builder builder(int size) {
    return new Builder(size);
  }

  public int size() {
    return valves.length;
  }

  public int positiveFlowRateValvesSize() {
    return positiveFlowRateValves.length;
  }

  public Iterator<Valve> neighbors(final int id) {
    return new ArrayItr<>(tunnels[id]);
  }

  public Iterator<Valve> positiveFlowRateValves() {
    return new ArrayItr<>(positiveFlowRateValves);
  }

  public Valve startValve() {
    return startValve;
  }

  @Override
  public Iterator<Valve> iterator() {
    return new ArrayItr<>(valves);
  }

  private static class ArrayItr<E> implements Iterator<E> {
    private int cursor;
    private final E[] a;

    ArrayItr(E[] a) {
      this.a = a;
    }

    @Override
    public boolean hasNext() {
      return cursor < a.length;
    }

    @Override
    public E next() {
      int i = cursor;
      if (i >= a.length) {
        throw new NoSuchElementException();
      }
      cursor = i + 1;
      return a[i];
    }
  }

  public static class Builder {

    private record ValveBuilder(String name, int flowRate, List<String> tunnels){}

    final int size;
    int current;
    ValveBuilder[] valves;

    public Builder(int size) {
      this.size = size;
      this.valves = new ValveBuilder[size];
    }

    public Builder add(final String name, final int flowRate, List<String> tunnels) {
      this.valves[current++] = new ValveBuilder(name, flowRate, tunnels);
      return this;
    }

    private int sort() {
      ValveBuilder t;
      int pos = 0;

      for (int i = 0; i < size; i++) {
        if (valves[i].flowRate > 0) {
          t = valves[pos];
          valves[pos++] = valves[i];
          valves[i] = t;
        }
      }

      return pos;
    }

    public Valves build() {
      final int positiveValves = sort();
      final var map = new HashMap<String, Valve>();
      final var valves = new Valve[size];
      final var positiveFlowRateValves = new Valve[positiveValves];
      final var tunnels = new Valve[size][];
      Valve startValve = null;

      int i = 0;
      for (; i < positiveValves; i++) {
        final var builder = this.valves[i];
        valves[i] = new Valve(i, builder.name, builder.flowRate);
        positiveFlowRateValves[i] = valves[i];
        map.put(builder.name, valves[i]);
      }
      for (; i < size; i++) {
        final var builder = this.valves[i];
        valves[i] = new Valve(i, builder.name, builder.flowRate);
        map.put(builder.name, valves[i]);
      }

      for (i = 0; i < size; i++) {
        final var builder = this.valves[i];
        if (builder.name.equals("AA")) {
          startValve = valves[i];
        }
        tunnels[i] = new Valve[builder.tunnels.size()];
        for (int j = 0; j < builder.tunnels.size(); j++) {
          tunnels[i][j] = map.get(builder.tunnels.get(j));
        }
      }

      return new Valves(startValve, valves, positiveFlowRateValves, tunnels);
    }
  }
}
