package nl.ramondevaan.aoc2022.day17;

import java.util.List;
import java.util.Objects;

public record State(List<Integer> depths, long round, long height, int rockCycle, int jetCycle) {
  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    final State state = (State) o;
    return rockCycle == state.rockCycle && jetCycle == state.jetCycle && depths.equals(state.depths);
  }

  @Override
  public int hashCode() {
    return Objects.hash(depths, rockCycle, jetCycle);
  }
}
