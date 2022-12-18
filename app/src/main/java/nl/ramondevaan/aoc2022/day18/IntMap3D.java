package nl.ramondevaan.aoc2022.day18;

public class IntMap3D {

  public final int xSize;
  public final int ySize;
  public final int zSize;
  public final int size;

  public final int xOffset;
  public final int yOffset;
  public final int zOffset;

  private final int[][][] map;

  private IntMap3D(int xSize, int ySize, int zSize, int size, int xOffset, int yOffset, int zOffset, int[][][] map) {
    this.xSize = xSize;
    this.ySize = ySize;
    this.zSize = zSize;
    this.size = size;

    this.xOffset = xOffset;
    this.yOffset = yOffset;
    this.zOffset = zOffset;

    this.map = map;
  }

  public int get(final int x, final int y, final int z) {
    return map[x][y][z];
  }

  public int getOffset(final int x, final int y, final int z) {
    return map[x - xOffset][y - yOffset][z - zOffset];
  }

  public static Builder builder(int xSize, int ySize, int zSize, int xOffset, int yOffset, int zOffset) {
    return new Builder(xSize, ySize, zSize, xOffset, yOffset, zOffset);
  }

  public static class Builder {
    public final int xSize;
    public final int ySize;
    public final int zSize;
    public final int size;

    public final int xOffset;
    public final int yOffset;
    public final int zOffset;

    private int[][][] map;

    public Builder(int xSize, int ySize, int zSize, int xOffset, int yOffset, int zOffset) {
      this.map = new int[xSize][ySize][zSize];

      this.xSize = xSize;
      this.ySize = ySize;
      this.zSize = zSize;
      this.size = xSize * ySize * zSize;

      this.xOffset = xOffset;
      this.yOffset = yOffset;
      this.zOffset = zOffset;
    }

    public int get(final int x, final int y, final int z) {
      return map[x][y][z];
    }

    public void set(final int x, final int y, final int z, final int value) {
      this.map[x][y][z] = value;
    }

    public void setOffset(final int x, final int y, final int z, final int value) {
      this.map[x - xOffset][y - yOffset][z - zOffset] = value;
    }

    public boolean isInRange(final int x, final int y, final int z) {
      return 0 <= x && x < xSize && 0 <= y && y < ySize && 0 <= z && z < zSize;
    }

    public IntMap3D build() {
      final var ret = new IntMap3D(xSize, ySize, zSize, size, xOffset, yOffset, zOffset, map);
      this.map = null;
      return ret;
    }
  }
}
