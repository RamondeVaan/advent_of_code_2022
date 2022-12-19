package nl.ramondevaan.aoc2022.day19;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class State {
  private final int[] materials;
  private final int[] robots;

  public int getMaterialAmount(final Material material) {
    return materials[material.index];
  }

  public int getRobotAmount(final Material material) {
    return robots[material.index];
  }

  public Integer timeToBuild(final Blueprint blueprint, final Material robot) {
    var maxTimeToBuild = Integer.MIN_VALUE;

    for (final var buildingMaterial : BuildingMaterial.values()) {
      final var buildingMaterialCost = blueprint.cost(robot, buildingMaterial);
      final var availableMaterial = materials[buildingMaterial.index];
      if (buildingMaterialCost <= availableMaterial) {
        maxTimeToBuild = Math.max(maxTimeToBuild, 1);
        continue;
      }
      final var robotsGatheringMaterial = robots[buildingMaterial.index];
      if (robotsGatheringMaterial == 0) {
        return null;
      }
      final var timeToBuild = (buildingMaterialCost - availableMaterial - 1) / robotsGatheringMaterial + 2;
      maxTimeToBuild = Math.max(maxTimeToBuild, timeToBuild);
    }

    return maxTimeToBuild;
  }

  public State build(final Blueprint blueprint, final Material robot, final int time) {
    final int[] materials = new int[this.materials.length];
    final int[] robots = new int[this.robots.length];
    System.arraycopy(this.robots, 0, robots, 0, robots.length);

    for (final Material material : Material.values()) {
      final var index = material.index;
      materials[index] = this.materials[index] + this.robots[index] * time - blueprint.cost(robot, material);
    }
    robots[robot.index]++;

    return new State(materials, robots);
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private int[] materials;
    private int[] robots;

    public Builder() {
      final var length = Material.values().length;
      this.materials = new int[length];
      this.robots = new int[length];
    }

    public Builder setMaterialAmount(final Material material, final int amount) {
      materials[material.index] = amount;
      return this;
    }

    public Builder setRobotAmount(final Material material, final int amount) {
      robots[material.index] = amount;
      return this;
    }

    public State build() {
      final var ret = new State(this.materials, this.robots);

      this.materials = null;
      this.robots = null;

      return ret;
    }
  }
}
