package nl.ramondevaan.aoc2022.day15;

import nl.ramondevaan.aoc2022.util.Parser;
import nl.ramondevaan.aoc2022.util.StringIteratorParser;

public class SensorParser implements Parser<String, Sensor> {

  private final static char[] SENSOR_START = new char[]{'S', 'e', 'n', 's', 'o', 'r', ' ', 'a', 't', ' '};
  private final static char[] CLOSEST = new char[]{':', ' ', 'c', 'l', 'o', 's', 'e', 's', 't', ' '};
  private final static char[] BEACON_START = new char[]{'b', 'e', 'a', 'c', 'o', 'n', ' ', 'i', 's', ' ', 'a', 't', ' '};
  private final static char[] X_START = new char[]{'x', '='};
  private final static char[] Y_START = new char[]{'y', '='};
  private final static char[] COMMA = new char[]{',', ' '};

  @Override
  public Sensor parse(String toParse) {
    final var parser = new StringIteratorParser(toParse);
    parser.consume(SENSOR_START);
    parser.consume(X_START);
    final var x = parser.parseInteger();
    parser.consume(COMMA);
    parser.consume(Y_START);
    final var y = parser.parseInteger();
    parser.consume(CLOSEST);
    parser.consume(BEACON_START);
    parser.consume(X_START);
    final var beaconX = parser.parseInteger();
    parser.consume(COMMA);
    parser.consume(Y_START);
    final var beaconY = parser.parseInteger();

    parser.verifyIsDone();

    return new Sensor(x, y, beaconX, beaconY);
  }
}
