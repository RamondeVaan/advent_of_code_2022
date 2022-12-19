package nl.ramondevaan.aoc2022.day19;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BuildingMaterial {
  ORE(Material.ORE.index), CLAY(Material.CLAY.index), OBSIDIAN(Material.OBSIDIAN.index);

  public final int index;
}
