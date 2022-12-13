package nl.ramondevaan.aoc2022.day13;

import nl.ramondevaan.aoc2022.util.BlankStringPartitioner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day13 {

  private final List<PacketPair> packetPairs;
  private final List<DataList> allPackets;

  public Day13(final List<String> lines) {
    final var partitioner = new BlankStringPartitioner();
    final var parser = new PacketPairParser();
    final var all = new ArrayList<DataList>();

    this.packetPairs = partitioner.partition(lines).stream().map(parser::parse).peek(pair -> {
      all.add(pair.left());
      all.add(pair.right());
    }).toList();
    this.allPackets = Collections.unmodifiableList(all);
  }

  public long solve1() {
    var sum = 0;
    var index = 1;

    for (final var packetPair : packetPairs) {
      sum += index++ * (compare(packetPair.left(), packetPair.right()) < 0 ? 1 : 0);
    }

    return sum;
  }

  public long solve2() {
    final var divider1 = new DataList(new DataList(new DataInteger(2)));
    final var divider2 = new DataList(new DataList(new DataInteger(6)));

    final var all = new ArrayList<DataList>(allPackets.size() + 2);
    all.addAll(allPackets);
    all.add(divider1);
    all.add(divider2);
    all.sort(this::compare);

    return (long) (all.indexOf(divider1) + 1) * (all.indexOf(divider2) + 1);
  }

  private int compare(final Data left, final Data right) {
    if (left instanceof DataInteger leftInteger) {
      if (right instanceof DataInteger rightInteger) {
        return leftInteger.value() - rightInteger.value();
      } else if (right instanceof DataList rightList) {
        return compare(new DataList(leftInteger), rightList);
      }
    } else if (left instanceof DataList leftList) {
      if (right instanceof DataInteger rightInteger) {
        return compare(leftList, new DataList(rightInteger));
      } else if (right instanceof DataList rightList) {
        final var min = Math.min(leftList.size(), rightList.size());
        for (int i = 0; i < min; i++) {
          final var comparison = compare(leftList.get(i), rightList.get(i));
          if (comparison != 0) {
            return comparison;
          }
        }
        return leftList.size() - rightList.size();
      }
    }

    throw new IllegalArgumentException();
  }
}
