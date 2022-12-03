package nl.ramondevaan.aoc2022.day03;

import lombok.RequiredArgsConstructor;
import nl.ramondevaan.aoc2022.util.Parser;
import reactor.core.publisher.Flux;

import static org.apache.commons.lang3.ArrayUtils.toObject;

@RequiredArgsConstructor
public class BagParser implements Parser<String, Bag> {

  private final int compartmentCount;

  @Override
  public Bag parse(String toParse) {
    final var compartmentSize = toParse.length() / compartmentCount;

    final var compartments = Flux.fromArray(toObject(toParse.toCharArray()))
        .buffer(compartmentSize)
        .toStream()
        .toList();

    return new Bag(compartments);
  }
}
