package nl.ramondevaan.aoc2022.day19;

import nl.ramondevaan.aoc2022.util.Parser;
import nl.ramondevaan.aoc2022.util.StringIteratorParser;

public class BlueprintParser implements Parser<String, Blueprint> {

  private final static char[] BLUEPRINT = new char[]{'B', 'l', 'u', 'e', 'p', 'r', 'i', 'n', 't', ' '};
  private final static char[] ORE_ROBOT = new char[]{':', ' ', 'E', 'a', 'c', 'h', ' ', 'o', 'r', 'e', ' ',
      'r', 'o', 'b', 'o', 't', ' ', 'c', 'o', 's', 't', 's', ' '};
  private final static char[] CLAY_ROBOT = new char[]{' ', 'E', 'a', 'c', 'h', ' ', 'c', 'l', 'a', 'y', ' ',
      'r', 'o', 'b', 'o', 't', ' ', 'c', 'o', 's', 't', 's', ' '};
  private final static char[] OBSIDIAN_ROBOT = new char[]{' ', 'E', 'a', 'c', 'h', ' ',
      'o', 'b', 's', 'i', 'd', 'i', 'a', 'n', ' ', 'r', 'o', 'b', 'o', 't', ' ', 'c', 'o', 's', 't', 's', ' '};
  private final static char[] GEODE_ROBOT = new char[]{' ', 'E', 'a', 'c', 'h', ' ',
      'g', 'e', 'o', 'd', 'e', ' ', 'r', 'o', 'b', 'o', 't', ' ', 'c', 'o', 's', 't', 's', ' '};
  private final static char[] ORE = new char[]{'r', 'e'};
  private final static char[] CLAY = new char[]{'c', 'l', 'a', 'y'};
  private final static char[] OBSIDIAN = new char[]{'b', 's', 'i', 'd', 'i', 'a', 'n'};
  private final static char[] AND = new char[]{' ', 'a', 'n', 'd', ' '};

  @Override
  public Blueprint parse(final String toParse) {
    final var builder = Blueprint.builder();
    final var parser = new StringIteratorParser(toParse);

    parser.consume(BLUEPRINT);
    builder.setId(parser.parseNumber());
    parser.consume(ORE_ROBOT);
    parseCost(Material.ORE, builder, parser);
    parser.consume(CLAY_ROBOT);
    parseCost(Material.CLAY, builder, parser);
    parser.consume(OBSIDIAN_ROBOT);
    parseCost(Material.OBSIDIAN, builder, parser);
    parser.consume(GEODE_ROBOT);
    parseCost(Material.GEODE, builder, parser);

    parser.verifyIsDone();

    return builder.build();
  }

  private void parseCost(final Material robot, final Blueprint.Builder builder, final StringIteratorParser parser) {
    do {
      final var number = parser.parseNumber();
      parser.consume(' ');
      switch (parser.current()) {
        case 'o' -> setObsidianOrOre(robot, builder, number, parser);
        case 'c' -> setClay(robot, builder, number, parser);
        default -> throw new IllegalArgumentException();
      }
    } while (parser.tryConsume(AND));

    parser.consume('.');
  }

  private void setClay(final Material robot, final Blueprint.Builder builder, final int number,
                       final StringIteratorParser parser) {
    parser.consume(CLAY);
    builder.setCost(robot, BuildingMaterial.CLAY, number);
  }

  private void setObsidianOrOre(final Material robot, final Blueprint.Builder builder, final int number,
                                final StringIteratorParser parser) {
    parser.consume('o');
    switch (parser.current()) {
      case 'r' -> {
        parser.consume(ORE);
        builder.setCost(robot, BuildingMaterial.ORE, number);
      }
      case 'b' -> {
        parser.consume(OBSIDIAN);
        builder.setCost(robot, BuildingMaterial.OBSIDIAN, number);
      }
      default -> throw new IllegalArgumentException();
    }
  }
}
