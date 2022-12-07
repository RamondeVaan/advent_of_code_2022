package nl.ramondevaan.aoc2022.day07;

import nl.ramondevaan.aoc2022.util.Parser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileSystemParser implements Parser<List<String>, FileSystem> {
  @Override
  public FileSystem parse(List<String> toParse) {
    final var iterator = toParse.iterator();
    final var directories = new ArrayList<Directory>();

    if (iterator.next().equals("$ cd /")) {
      final var root = read("/", iterator, directories);
      return new FileSystem(List.copyOf(directories), root);
    }

    throw new IllegalArgumentException();
  }

  public Directory read(final String name, Iterator<String> iterator, final List<Directory> result) {
    final var directories = new ArrayList<Directory>();
    final var files = new ArrayList<File>();
    var size = 0L;

    while (iterator.hasNext()) {
      final var line = iterator.next();
      if (line.equals("$ cd ..")) {
        break;
      } else if (line.startsWith("$ cd")) {
        final var directory = read(line.substring(5), iterator, result);
        directories.add(directory);
        size += directory.size();
      } else if (Character.isDigit(line.charAt(0))) {
        final var split = line.split("\\s+");
        final var file = new File(split[1], Long.parseLong(split[0]));
        files.add(file);
        size += file.size();
      }
    }

    final var directory = new Directory(name, List.copyOf(directories), List.copyOf(files), size);
    result.add(directory);
    return directory;
  }
}
