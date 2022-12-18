package nl.ramondevaan.aoc2022.day17;

import nl.ramondevaan.aoc2022.util.Cycle;
import nl.ramondevaan.aoc2022.util.IntMap;
import nl.ramondevaan.aoc2022.util.MutableIntMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day17 {

  private final List<IntMap> rockTypes;
  private final List<Jet> jets;
  private final int rockBits;

  public Day17(final List<String> lines) {
    final var rockTypeParser = new RockTypeParser();
    this.rockTypes = rockTypeParser.parse(Day17.class.getResource("/day_17_rock_types.txt"));
    this.rockBits = Integer.SIZE - Integer.numberOfLeadingZeros(rockTypes.size());

    final var jetParser = new JetParser();
    this.jets = jetParser.parse(lines);
  }

  public long solve1() {
    return solve(2022);
  }

  public long solve2() {
    return solve(1_000_000_000_000L);
  }

  private int drop(final MutableIntMap map, final IntMap rock, final Cycle<Jet> jetCycle, final int height) {
    var maxColumnOffset = map.columns() - rock.columns();
    var columnOffset = 2;
    var rowOffset = height;
    int nextColumnOffset;
    int nextRowOffset;

    while (true) {
      nextColumnOffset = columnOffset + jetCycle.next().columnOffset;
      if (nextColumnOffset >= 0 && nextColumnOffset <= maxColumnOffset && !collides(map, rock, rowOffset, nextColumnOffset)) {
        columnOffset = nextColumnOffset;
      }
      nextRowOffset = rowOffset - 1;
      if (nextRowOffset < 0 || collides(map, rock, nextRowOffset, columnOffset)) {
        break;
      }
      rowOffset = nextRowOffset;
    }

    set(map, rock, rowOffset, columnOffset);
    return rowOffset;
  }

  private boolean collides(final MutableIntMap map, final IntMap rock, final int rowOffset, final int columnOffset) {
    for (int row = 0, mapRow = rowOffset; row < rock.rows(); row++, mapRow++) {
      for (int column = 0, mapColumn = columnOffset; column < rock.columns(); column++, mapColumn++) {
        if (rock.valueAt(row, column) > 0 && map.valueAt(mapRow, mapColumn) > 0) {
          return true;
        }
      }
    }

    return false;
  }

  private void set(final MutableIntMap map, final IntMap rock, final int rowOffset, final int columnOffset) {
    for (int row = 0, mapRow = rowOffset; row < rock.rows(); row++, mapRow++) {
      for (int column = 0, mapColumn = columnOffset; column < rock.columns(); column++, mapColumn++) {
        final var value = rock.valueAt(row, column);
        if (value > 0) {
          map.setValueAt(mapRow, mapColumn, value);
        }
      }
    }
  }

  public long solve(final long rounds) {
    final var map = new MutableIntMap(10_000, 7);
    final var rockCycle = new Cycle<>(rockTypes);
    final var jetCycle = new Cycle<>(jets);
    final var states = new HashMap<Integer, State>();

    var height = 0;
    var heightOffset = 0L;
    var round = 0L;
    for (;round < rounds; round++) {
      final var rock = rockCycle.next();
      height = Math.max(rock.rows() + drop(map, rock, jetCycle, height + 3), height);
      final var state = getCycle(map, height, round, states, rockCycle, jetCycle);
      if (state != null) {
        final var length = round - state.round();
        final var remaining = rounds - round;
        final var times = remaining / length;
        final var heightDiff = height - state.height();
        heightOffset += times * heightDiff;
        round += length * times + 1;
        break;
      }
    }
    for (;round < rounds; round++) {
      final var rock = rockCycle.next();
      height = Math.max(rock.rows() + drop(map, rock, jetCycle, height + 3), height);
    }

    return height + heightOffset;
  }

  private State getCycle(final MutableIntMap map, final int height, long round, final Map<Integer, State> states,
                         final Cycle<IntMap> rockCycle, final Cycle<Jet> jetCycle) {
    final var topRow = map.row(height - 1);
    for (final var value : topRow) {
      if (value != 1) {
        return null;
      }
    }
    final var rockIndex = rockCycle.nextIndex();
    final var jetIndex = jetCycle.nextIndex();
    final var id = (jetIndex << rockBits) | rockIndex;
    final var state = states.get(id);
    if (state != null) {
      return state;
    }
    states.put(id, new State(round, height, rockIndex, jetIndex));
    return null;
  }
}
