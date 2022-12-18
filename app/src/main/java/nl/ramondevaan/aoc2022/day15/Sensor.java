package nl.ramondevaan.aoc2022.day15;

public class Sensor {
  public final int x;
  public final int y;
  public final int beaconX;
  public final int beaconY;
  public final int range;

  public Sensor(int x, int y, int beaconX, int beaconY) {
    this.x = x;
    this.y = y;
    this.beaconX = beaconX;
    this.beaconY = beaconY;
    this.range = Math.abs(beaconX - x) + Math.abs(beaconY - y);
  }
}
