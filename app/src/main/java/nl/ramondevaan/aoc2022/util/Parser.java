package nl.ramondevaan.aoc2022.util;

public interface Parser<T, U> {
    U parse(T toParse);
}
