package nl.ramondevaan.aoc2022.day16;

import nl.ramondevaan.aoc2022.util.IntMap;

import java.util.ArrayDeque;
import java.util.List;

public class Day16 {

  private final Valves valves;
  private final IntMap distanceMap;

  public Day16(final List<String> lines) {
    final var parser = new ValvesParser();

    this.valves = parser.parse(lines);
    this.distanceMap = distanceMap();
  }

  public long solve1() {
    final var scores = calculateMaxPressure(30);

    var best = 0;

    for (int j = 0; j < scores[0].length; j++) {
      for (int k = 0; k < scores[0][j].length; k++) {
        best = Math.max(best, scores[0][j][k]);
      }
    }

    return best;
  }

  private int[][][] calculateMaxPressure(final int minutes) {
    final var queue = new ArrayDeque<State>(1_000_000);
    final var max = (1 << valves.positiveFlowRateValvesSize());
    var scores = new int[minutes][valves.positiveFlowRateValvesSize()][max];
    final var startValve = valves.startValve();
    if (startValve.flowRate > 0) {
      queue.add(new State(valves.startValve(), 0, 0, minutes));
    } else {
      final var iterator = valves.positiveFlowRateValves();
      while (iterator.hasNext()) {
        final var valve = iterator.next();
        final var timeRemaining = minutes - distanceMap.valueAt(startValve.id, valve.id) - 1;
        if (timeRemaining >= 0) {
          final var nextUsed = (1 << valve.id);
          final var nextScore = valve.flowRate * timeRemaining;
          for (int i = timeRemaining; i >= 0; i--) {
            scores[i][valve.id][nextUsed] = Math.max(scores[i][valve.id][nextUsed], nextScore);
          }
          queue.add(new State(valve, nextScore, nextUsed, timeRemaining));
        }
      }
    }

    State state;
    while ((state = queue.poll()) != null) {
      final var nextTime = state.timeRemaining - 1;
      final var id = state.position.id;
      if (nextTime >= 0 && (state.used & (1 << id)) == 0) {
        final var nextUsed = state.used | (1 << id);
        final var nextScore = state.pressure + state.position.flowRate * state.timeRemaining;
        for (int i = nextTime; i >= 0; i--) {
          scores[i][id][nextUsed] = Math.max(scores[i][id][nextUsed], nextScore);
        }
        queue.add(new State(state.position, nextScore, nextUsed, nextTime));
      }

      final var iterator = valves.positiveFlowRateValves();
      while (iterator.hasNext()) {
        final var valve = iterator.next();
        if ((state.used & (1 << valve.id)) == 0) {
          final var timeRemaining = state.timeRemaining - distanceMap.valueAt(id, valve.id) - 1;
          if (timeRemaining >= 0) {
            final var nextUsed = state.used | (1 << valve.id);
            final var nextScore = state.pressure + valve.flowRate * timeRemaining;
            for (int i = timeRemaining; i >= 0; i--) {
              scores[i][valve.id][nextUsed] = Math.max(scores[i][valve.id][nextUsed], nextScore);
            }
            queue.add(new State(valve, nextScore, nextUsed, timeRemaining));
          }
        }
      }
    }

    return scores;
  }

  private IntMap distanceMap() {
    final var distanceMap = IntMap.builder(valves.size(), valves.size());
    final var queue = new ArrayDeque<Valve>(valves.size());
    distanceMap.fill(Integer.MAX_VALUE);

    for (final var from : valves) {
      distanceMap.set(from.id, from.id, 0);
      queue.add(from);

      Valve current;
      while ((current = queue.poll()) != null) {
        final var nextDistance = distanceMap.get(from.id, current.id) + 1;
        final var iterator = valves.neighbors(current.id);
        while (iterator.hasNext()) {
          final var neighbor = iterator.next();
          if (distanceMap.get(from.id, neighbor.id) == Integer.MAX_VALUE) {
            distanceMap.set(from.id, neighbor.id, nextDistance);
            queue.add(neighbor);
          }
        }
      }
    }

    return distanceMap.build();
  }

  public long solve2() {
    final var max = (1 << valves.positiveFlowRateValvesSize());
    var scoreByFinalState = new int[max];

    final var scores = calculateMaxPressure(26);
    for (int j = 0; j < scores[0].length; j++) {
      for (int k = 0; k < max; k++) {
        scoreByFinalState[k] = Math.max(scoreByFinalState[k], scores[0][j][k]);
      }
    }

    var best = 0;
    var all = max - 1;

    for (int state = 0; state < max; state++) {
      var inverse = all ^ state;
      var other = inverse;
      do {
        best = Math.max(best, scoreByFinalState[state] + scoreByFinalState[other]);
        other = (other - 1) & inverse;
      } while (other > 0);
    }

    return best;
  }
}
