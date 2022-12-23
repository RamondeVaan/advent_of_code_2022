package nl.ramondevaan.aoc2022.day21;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class Monkeys {
  public final Map<String, Monkey> monkeyMap;
  public final ComputingMonkey root;
  public final ConstantMonkey human;
}
