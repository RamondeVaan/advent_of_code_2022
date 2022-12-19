package nl.ramondevaan.aoc2022.day19;

import java.util.List;

import static nl.ramondevaan.aoc2022.day19.Material.GEODE;

public class Day19 {

  private final List<Blueprint> blueprints;

  public Day19(final List<String> lines) {
    final var parser = new BlueprintParser();
    this.blueprints = lines.stream().map(parser::parse).toList();
  }

  public long solve1() {
    final var minutes = 24;
    final var initialState = State.builder().setRobotAmount(Material.ORE, 1).build();
    var sum = 0;

    for (final var blueprint : blueprints) {
      sum += getMaxGeodes(blueprint, minutes, initialState, Integer.MIN_VALUE) * blueprint.id;
    }

    return sum;
  }

  public long solve2() {
    final var minutes = 32;
    final var initialState = State.builder().setRobotAmount(Material.ORE, 1).build();

    var multiplied = 1;

    for (int i = 0; i < 3; i++) {
      multiplied *= getMaxGeodes(blueprints.get(i), minutes, initialState, Integer.MIN_VALUE);
    }

    return multiplied;
  }

  private int getMaxGeodes(final Blueprint blueprint, final int minutes, final State state, int max) {
    var geodesIfNothingBuilt = state.getMaterialAmount(GEODE) + state.getRobotAmount(GEODE) * minutes;
    max = Math.max(geodesIfNothingBuilt, max);

    if (minutes <= 1 || (minutes * minutes - minutes) / 2 + geodesIfNothingBuilt < max) {
      return max;
    }

    for (final Material robot : Material.values()) {
      if (state.getRobotAmount(robot) >= blueprint.maxRobotAmount(robot)) {
        continue;
      }

      final var timeToBuild = state.timeToBuild(blueprint, robot);
      if (timeToBuild == null || timeToBuild >= minutes) {
        continue;
      }

      final var newState = state.build(blueprint, robot, timeToBuild);

      max = getMaxGeodes(blueprint, minutes - timeToBuild, newState, max);
    }

    return max;
  }
}
