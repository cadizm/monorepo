package com.cadizm.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Stream;

public class Resource {

  private static final ClassLoader classLoader = Resource.class.getClassLoader();

  public static InputStream getResourceAsStream(String resourcePath) {
    // Use ClassLoader instead of class (i.e. Resource.class.getResourceAsStream)
    // to read resource in order to search for absolute path from root of classpath
    return classLoader.getResourceAsStream(resourcePath);
  }

  public static Stream<String> readLines(String resourcePath) {
    Stream.Builder<String> streamBuilder = Stream.builder();

    try (InputStream is = getResourceAsStream(resourcePath)) {
      if (is == null) {
        throw new RuntimeException("Unable to locate resource " + resourcePath);
      }

      BufferedReader br = new BufferedReader(new InputStreamReader(is));
      String line;

      while ((line = br.readLine()) != null) {
        streamBuilder.add(line);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    return streamBuilder.build();
  }
}
