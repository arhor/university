package by.arhor.university.domain.repository.impl;

import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class RepositoryUtils {

  private RepositoryUtils() { throw new UnsupportedOperationException("Must not be instantiated"); }

  static String columns(String... names) {
    return Stream.of(names)
        .map(name -> '[' + name + ']')
        .collect(joining(",", " ", " "));
  }

}
