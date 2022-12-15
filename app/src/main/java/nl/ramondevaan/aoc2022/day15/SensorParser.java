package nl.ramondevaan.aoc2022.day15;

import nl.ramondevaan.aoc2022.util.Parser;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class SensorParser implements Parser<String, Sensor> {

    private final static char[] SENSOR_START = new char[]{'S', 'e', 'n', 's', 'o', 'r', ' ', 'a', 't', ' '};
    private final static char[] CLOSEST = new char[] {':', ' ', 'c', 'l', 'o', 's', 'e', 's', 't', ' '};
    private final static char[] BEACON_START = new char[]{'b', 'e', 'a', 'c', 'o', 'n', ' ', 'i', 's', ' ', 'a', 't', ' '};
    private final static char[] X_START = new char[]{'x', '='};
    private final static char[] Y_START = new char[]{'y', '='};
    private final static char[] COMMA = new char[]{',', ' '};

    @Override
    public Sensor parse(String toParse) {
        final var iterator = new StringCharacterIterator(toParse);
        consume(iterator, SENSOR_START);
        consume(iterator, X_START);
        final var x = parseNumber(iterator);
        consume(iterator, COMMA);
        consume(iterator, Y_START);
        final var y = parseNumber(iterator);
        consume(iterator, CLOSEST);
        consume(iterator, BEACON_START);
        consume(iterator, X_START);
        final var beaconX = parseNumber(iterator);
        consume(iterator, COMMA);
        consume(iterator, Y_START);
        final var beaconY = parseNumber(iterator);

        return new Sensor(x, y, beaconX, beaconY);
    }

    private int parseNumber(final StringCharacterIterator iterator) {
        final var builder = new StringBuilder();
        builder.append(iterator.current());

        while (iterator.next() != CharacterIterator.DONE && Character.isDigit(iterator.current())) {
            builder.append(iterator.current());
        }

        return Integer.parseInt(builder.toString());
    }

    private void consume(final StringCharacterIterator iterator, final char[] chars) {
        if (iterator.current() == CharacterIterator.DONE) {
            throw new IllegalArgumentException();
        }
        for (final var expected : chars) {
            if (iterator.current() != expected) {
                throw new IllegalArgumentException(String.format("Unexpected token: %s", iterator.current()));
            }
            iterator.next();
        }
    }
}
