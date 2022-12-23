package nl.ramondevaan.aoc2022.day21;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2022.util.Parser;
import nl.ramondevaan.aoc2022.util.StringIteratorParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonkeysParser implements Parser<List<String>, Monkeys> {

  private final static char[] COLON = new char[] {':', ' '};

  @Override
  public Monkeys parse(final List<String> toParse) {
    final var builderMap = new HashMap<String, MonkeyBuilder>();
    final var resultMap = new HashMap<String, Monkey>();

    for (final var line : toParse) {
      parse(builderMap, line);
    }
    final var root = (ComputingMonkey) builderMap.get("root").build(builderMap, resultMap);

    return new Monkeys(resultMap, root, (ConstantMonkey) resultMap.get("humn"));
  }

  private void parse(final Map<String, MonkeyBuilder> builderMap, final String toParse) {
    final var parser = new StringIteratorParser(toParse);

    final var name = parser.parseString();
    final var builder = builder(builderMap, name);
    parser.consume(COLON);

    if (Character.isAlphabetic(parser.current())) {
      builder.left = parser.parseString();
      parser.consume(' ');
      builder.operator = parser.current();
      parser.consume();
      parser.consume(' ');
      builder.right = parser.parseString();
    } else {
      builder.number = parser.parseLong();
    }

    parser.verifyIsDone();
  }

  private MonkeyBuilder builder(final Map<String, MonkeyBuilder> map, final String name) {
    MonkeyBuilder builder;

    if ((builder = map.get(name)) == null) {
      map.put(name, builder = new MonkeyBuilder(name));
    }

    return builder;
  }

  @RequiredArgsConstructor
  private static class MonkeyBuilder {
    public final String name;
    public String left;
    public String right;
    public char operator;
    public Long number;

    public Monkey build(final Map<String, MonkeyBuilder> builderMap, final Map<String, Monkey> resultMap) {
      if (resultMap.containsKey(name)) {
        return resultMap.get(name);
      }
      if (number != null) {
        final var monkey = new ConstantMonkey(name, number);
        resultMap.put(name, monkey);
        return monkey;
      }

      final var leftMonkey = builderMap.get(left).build(builderMap, resultMap);
      final var rightMonkey = builderMap.get(right).build(builderMap, resultMap);

      final var monkey = switch (operator) {
        case '+' -> new AddingMonkey(name, leftMonkey, rightMonkey);
        case '*' -> new MultiplyingMonkey(name, leftMonkey, rightMonkey);
        case '/' -> new DividingMonkey(name, leftMonkey, rightMonkey);
        case '-' -> new SubtractingMonkey(name, leftMonkey, rightMonkey);
        default -> throw new IllegalArgumentException();
      };
      resultMap.put(name, monkey);
      return monkey;
    }
  }
}
