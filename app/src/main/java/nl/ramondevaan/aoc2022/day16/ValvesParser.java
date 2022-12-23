package nl.ramondevaan.aoc2022.day16;

import nl.ramondevaan.aoc2022.util.Parser;
import nl.ramondevaan.aoc2022.util.StringIteratorParser;

import java.util.ArrayList;
import java.util.List;

public class ValvesParser implements Parser<List<String>, Valves> {

  private final static char[] VALVE = new char[]{'V', 'a', 'l', 'v', 'e', ' '};
  private final static char[] FLOW_RATE = new char[]{' ', 'h', 'a', 's', ' ', 'f', 'l', 'o', 'w', ' ',
      'r', 'a', 't', 'e', '='};
  private final static char[] TUNNEL = new char[]{';', ' ', 't', 'u', 'n', 'n', 'e', 'l'};
  private final static char[] LEAD = new char[]{' ', 'l', 'e', 'a', 'd'};
  private final static char[] LEADS = new char[]{' ', 'l', 'e', 'a', 'd', 's'};
  private final static char[] TO_VALVE = new char[]{' ', 't', 'o', ' ', 'v', 'a', 'l', 'v', 'e', ' '};
  private final static char[] TO_VALVES = new char[]{' ', 't', 'o', ' ', 'v', 'a', 'l', 'v', 'e', 's', ' '};
  private final static char[] COMMA = new char[]{',', ' '};

  @Override
  public Valves parse(final List<String> toParse) {
    final var builder = Valves.builder(toParse.size());

    for (String s : toParse) {
      parseValve(s, builder);
    }

    return builder.build();
  }

  private void parseValve(final String toParse, final Valves.Builder builder) {
    final var parser = new StringIteratorParser(toParse);
    parser.consume(VALVE);
    final var name = parser.parseString();
    parser.consume(FLOW_RATE);
    final var flowRate = parser.parseInteger();
    parser.consume(TUNNEL);
    if (parser.consumeIfPresent('s')) {
      parser.consume(LEAD);
      parser.consume(TO_VALVES);
    } else {
      parser.consume(LEADS);
      parser.consume(TO_VALVE);
    }
    final var names = parseTunnelNames(parser);

    builder.add(name, flowRate, names);
  }

  private List<String> parseTunnelNames(final StringIteratorParser parser) {
    final var names = new ArrayList<String>();

    do {
      names.add(parser.parseString());
    } while (parser.consumeIfNotDone(COMMA));

    return names;
  }
}
