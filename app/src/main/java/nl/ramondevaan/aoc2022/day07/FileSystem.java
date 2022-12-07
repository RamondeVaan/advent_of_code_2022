package nl.ramondevaan.aoc2022.day07;

import java.util.List;

public record FileSystem(List<Directory> directories, Directory root) {
}
