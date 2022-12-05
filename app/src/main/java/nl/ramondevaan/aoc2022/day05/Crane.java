package nl.ramondevaan.aoc2022.day05;

import java.util.Deque;

@FunctionalInterface
public interface Crane {
  void move(final Deque<Character> from, final Deque<Character> to, final int crates);
}
