package nl.ramondevaan.aoc2022.day03;

import com.google.common.collect.Sets;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toUnmodifiableSet;

public class Day03 {

  private final Map<Character, Integer> priorityMap;
  private final List<Bag> bags;

  public Day03(final List<String> lines) {
    final var parser = new BagParser(2);
    this.bags = lines.stream().map(parser::parse).toList();
    this.priorityMap = Stream.concat(
        IntStream.rangeClosed('a', 'z').mapToObj(i -> Map.entry((char) i, i - 'a' + 1)),
        IntStream.rangeClosed('A', 'Z').mapToObj(i -> Map.entry((char) i, i - 'A' + 27))
    ).collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  public long solve1() {
    return bags.stream()
        .map(Bag::compartments)
        .map(this::getDuplicate)
        .mapToLong(priorityMap::get).sum();
  }

  public long solve2() {
    return Flux.fromIterable(bags)
        .map(this::itemTypes)
        .buffer(3)
        .map(this::getDuplicate)
        .toStream()
        .mapToLong(priorityMap::get)
        .sum();
  }

  private Set<Character> itemTypes(final Bag bag) {
    return bag.compartments().stream().flatMap(List::stream).collect(toUnmodifiableSet());
  }

  private char getDuplicate(final Collection<? extends Collection<Character>> collections) {
    return collections.stream().map(Set::copyOf).reduce(Sets::intersection).orElseThrow()
        .stream().findFirst().orElseThrow();
  }
}
