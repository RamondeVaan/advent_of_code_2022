package nl.ramondevaan.aoc2022.day05;

import nl.ramondevaan.aoc2022.util.BlankStringPartitioner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toCollection;

public class Day05 {

  private final List<List<Character>> stacks;
  private final List<Step> procedure;

  public Day05(final List<String> lines) {
    final var partitioner = new BlankStringPartitioner();
    final var partitions = partitioner.partition(lines);

    final var stackParser = new StackParser();
    final var stepParser = new StepParser();

    this.stacks = stackParser.parse(partitions.get(0));
    this.procedure = partitions.get(1).stream().map(stepParser::parse).toList();
  }

  public String solve1() {
    final Crane crateMover9000 = (from, to, crates) -> Stream.generate(from::pop).limit(crates).forEach(to::push);

    return solve(crateMover9000);
  }

  public String solve2() {
    final Crane crateMover9001 = (from, to, crates) -> Stream.generate(from::pop).limit(crates)
        .collect(toCollection(LinkedList::new)).descendingIterator().forEachRemaining(to::push);

    return solve(crateMover9001);
  }

  private String solve(final Crane crane) {
    final var stacks = this.stacks.stream().map(LinkedList::new).toList();

    procedure.forEach(step -> crane.move(stacks.get(step.fromIndex()), stacks.get(step.toIndex()), step.crates()));

    return stacks.stream().map(Deque::element).map(Object::toString).collect(Collectors.joining());
  }
}
