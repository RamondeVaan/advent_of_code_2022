package nl.ramondevaan.aoc2022.day10;

import nl.ramondevaan.aoc2022.util.Parser;

public class InstructionParser implements Parser<String, Instruction> {
  @Override
  public Instruction parse(String toParse) {
    return toParse.startsWith("n") ? new Noop() : new AddX(Integer.parseInt(toParse.substring(5)));
  }
}
