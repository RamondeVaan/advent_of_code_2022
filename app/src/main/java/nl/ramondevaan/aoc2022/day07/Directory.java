package nl.ramondevaan.aoc2022.day07;

import java.util.List;

public record Directory(String name, List<Directory> directories, List<File> files, long size) {
}
