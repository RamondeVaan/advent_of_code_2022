package nl.ramondevaan.aoc2022.day19;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Blueprint {
  public final int id;
  private final int[][] cost;
  private final int[] maxRobot;

  public int cost(final Material robot, final BuildingMaterial material) {
    return cost[robot.index][material.index];
  }

  public int cost(final Material robot, final Material material) {
    return cost[robot.index][material.index];
  }

  public int maxRobotAmount(final Material material) {
    return maxRobot[material.index];
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private int id;
    private int[][] cost;
    private int[] maxRobot;

    public Builder() {
      final var length = Material.values().length;
      this.cost = new int[length][length];
      this.maxRobot = new int[length];
      this.maxRobot[Material.GEODE.index] = Integer.MAX_VALUE;
    }

    public Builder setId(final int id) {
      this.id = id;
      return this;
    }

    public Builder setCost(final Material robot, final BuildingMaterial buildingMaterial, final int amount) {
      this.cost[robot.index][buildingMaterial.index] = amount;
      this.maxRobot[buildingMaterial.index] = Math.max(this.maxRobot[buildingMaterial.index], amount);
      return this;
    }

    public Blueprint build() {
      final var ret = new Blueprint(id, cost, maxRobot);

      this.cost = null;
      this.maxRobot = null;

      return ret;
    }
  }
}
