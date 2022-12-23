package nl.ramondevaan.aoc2022.day22;

import nl.ramondevaan.aoc2022.util.Parser;
import nl.ramondevaan.aoc2022.util.StringIteratorParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PathParser implements Parser<String, List<Instruction>> {
  @Override
  public List<Instruction> parse(final String toParse) {
    final var parser = new StringIteratorParser(toParse);

    final var path = new ArrayList<Instruction>();

    while (parser.hasNext()) {
      if (Character.isDigit(parser.current())) {
        path.add(new Forward(parser.parseInteger()));
      } else {
        if (parser.current() == 'R') {
          path.add(new RotateClockwise());
        } else if (parser.current() == 'L') {
          path.add(new RotateCounterClockwise());
        } else {
          throw new IllegalArgumentException();
        }
        parser.consume();
      }
    }

    return Collections.unmodifiableList(path);
  }
}
