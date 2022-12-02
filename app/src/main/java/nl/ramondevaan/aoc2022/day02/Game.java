package nl.ramondevaan.aoc2022.day02;

public record Game(char opponent, char mine) {

  @Override
  public int hashCode() {
    return (opponent << 16) | mine;
  }
}
