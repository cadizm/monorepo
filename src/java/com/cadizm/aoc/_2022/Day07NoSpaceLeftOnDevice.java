package com.cadizm.aoc._2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.cadizm.io.Resource;

/*
 * --- Day 7: No Space Left On Device ---
 * You can hear birds chirping and raindrops hitting leaves as the expedition proceeds.
 * Occasionally, you can even hear much louder sounds in the distance; how big do the animals get
 * out here, anyway?
 *
 * The device the Elves gave you has problems with more than just its communication system. You try
 * to run a system update:
 *
 * $ system-update --please --pretty-please-with-sugar-on-top
 * Error: No space left on device
 * Perhaps you can delete some files to make space for the update?
 *
 * You browse around the filesystem to assess the situation and save the resulting terminal output
 * (your puzzle input). For example:
 *
 * $ cd /
 * $ ls
 * dir a
 * 14848514 b.txt
 * 8504156 c.dat
 * dir d
 * $ cd a
 * $ ls
 * dir e
 * 29116 f
 * 2557 g
 * 62596 h.lst
 * $ cd e
 * $ ls
 * 584 i
 * $ cd ..
 * $ cd ..
 * $ cd d
 * $ ls
 * 4060174 j
 * 8033020 d.log
 * 5626152 d.ext
 * 7214296 k
 *
 * The filesystem consists of a tree of files (plain data) and directories (which can contain other
 * directories or files). The outermost directory is called /. You can navigate around the
 * filesystem, moving into or out of directories and listing the contents of the directory you're
 * currently in.
 *
 * Within the terminal output, lines that begin with $ are commands you executed, very much like
 * some modern computers:
 *
 * cd means change directory. This changes which directory is the current directory, but the
 * specific result depends on the argument:
 * cd x moves in one level: it looks in the current directory for the directory named x and makes it
 * the current directory.
 * cd .. moves out one level: it finds the directory that contains the current directory, then makes
 * that directory the current directory.
 * cd / switches the current directory to the outermost directory, /.
 * ls means list. It prints out all of the files and directories immediately contained by the
 * current directory:
 * 123 abc means that the current directory contains a file named abc with size 123.
 * dir xyz means that the current directory contains a directory named xyz.
 * Given the commands and output in the example above, you can determine that the filesystem looks
 * visually like this:
 *
 * - / (dir)
 *   - a (dir)
 *     - e (dir)
 *       - i (file, size=584)
 *     - f (file, size=29116)
 *     - g (file, size=2557)
 *     - h.lst (file, size=62596)
 *   - b.txt (file, size=14848514)
 *   - c.dat (file, size=8504156)
 *   - d (dir)
 *     - j (file, size=4060174)
 *     - d.log (file, size=8033020)
 *     - d.ext (file, size=5626152)
 *     - k (file, size=7214296)
 *
 * Here, there are four directories: / (the outermost directory), a and d (which are in /), and e
 * (which is in a). These directories also contain files of various sizes.
 *
 * Since the disk is full, your first step should probably be to find directories that are good
 * candidates for deletion. To do this, you need to determine the total size of each directory. The
 * total size of a directory is the sum of the sizes of the files it contains, directly or
 * indirectly. (Directories themselves do not count as having any intrinsic size.)
 *
 * The total sizes of the directories above can be found as follows:
 *
 * The total size of directory e is 584 because it contains a single file i of size 584 and no other
 * directories.
 * The directory a has total size 94853 because it contains files f (size 29116), g (size 2557), and
 * h.lst (size 62596), plus file i indirectly (a contains e which contains i).
 * Directory d has total size 24933642.
 * As the outermost directory, / contains every file. Its total size is 48381165, the sum of the
 * size of every file.
 * To begin, find all of the directories with a total size of at most 100000, then calculate the sum
 * of their total sizes. In the example above, these directories are a and e; the sum of their total
 * sizes is 95437 (94853 + 584). (As in this example, this process can count files more than once!)
 *
 * Find all of the directories with a total size of at most 100000. What is the sum of the total
 * sizes of those directories?
 */
public class Day07NoSpaceLeftOnDevice {

  public static long dirsUnder100k() {
    List<String> lines = Resource.readLines("day07.input")
        .collect(Collectors.toList());

    Dir root = new Dir("/", null);
    new TerminalOutputInterpreter(lines, root).run();

    List<Dir> dirs = new ArrayList<>();

    Predicate<File> sizeUnder100k = file -> file.size() <= 100_000;
    dfs(root, 0, dirs, sizeUnder100k);

    return dirs.stream()
        .map(Dir::size)
        .reduce(0L, Long::sum);
  }

  /*
   * --- Part Two ---
   * Now, you're ready to choose a directory to delete.
   *
   * The total disk space available to the filesystem is 70000000. To run the update, you need
   * unused space of at least 30000000. You need to find a directory you can delete that will free
   * up enough space to run the update.
   *
   * In the example above, the total size of the outermost directory (and thus the total amount of
   * used space) is 48381165; this means that the size of the unused space must currently be
   * 21618835, which isn't quite the 30000000 required by the update. Therefore, the update still
   * requires a directory with total size of at least 8381165 to be deleted before it can run.
   *
   * To achieve this, you have the following options:
   *
   * Delete directory e, which would increase unused space by 584.
   * Delete directory a, which would increase unused space by 94853.
   * Delete directory d, which would increase unused space by 24933642.
   * Delete directory /, which would increase unused space by 48381165.
   *
   * Directories e and a are both too small; deleting them would not free up enough space. However,
   * directories d and / are both big enough! Between these, choose the smallest: d, increasing
   * unused space by 24933642.
   *
   * Find the smallest directory that, if deleted, would free up enough space on the filesystem to
   * run the update. What is the total size of that directory?
   */
  public static long findDirToFreeSpace() {
    List<String> lines = Resource.readLines("day07.input")
        .collect(Collectors.toList());

    Dir root = new Dir("/", null);
    new TerminalOutputInterpreter(lines, root).run();

    List<Dir> dirs = new ArrayList<>();

    long total = 70_000_000L;
    long available = total - root.size();
    long updateSize = 30_000_000L;

    Predicate<File> allowsUpdate = file -> available + file.size() >= updateSize;
    dfs(root, 0, dirs, allowsUpdate);

    // System.out.println(dirs);

    return dirs.stream()
        .map(Dir::size)
        .min(Long::compare)
        .orElseThrow(RuntimeException::new);
  }

  static void dfs(File file, int depth, List<Dir> dirs, Predicate<File> predicate) {
    if (file == null) {
      return;
    }

    // String spaces = String.format(String.format("%%1$%ds", depth * 2 + 1), "");
    // System.out.println(String.format("%s- %s", spaces, file));

    if (!(file instanceof Dir)) {
      return;
    }

    if (predicate.test(file)) {
      dirs.add((Dir)file);
    }

    for (File child : ((Dir)file).contents.values()) {
      dfs(child, depth + 1, dirs, predicate);
    }
  }

  static class TerminalOutputInterpreter {
    final List<String> input;
    final Dir root;

    public TerminalOutputInterpreter(List<String> input, Dir root) {
      this.input = input;
      this.root = root;
    }

    public void run() {
      ListIterator<String> it = input.listIterator();

      Dir dir = root;

      while (it.hasNext()) {
        dir = handleCommand(it, dir);
      }
    }

    Dir handleCommand(ListIterator<String> it, Dir dir) {
      assert it != null;

      String line = it.next();
      assert line.startsWith("$");

      String command = line.replace("$ ", "");

      // System.out.println(String.format("Processing cmd: %s", command));

      if (command.equals("ls")) {
        list(dir, it);
        return dir;
      } else if (command.startsWith("cd ")) {
        String dst = command.replace("cd ", "");
        return changeDirectory(dir, dst);
      }

      throw new RuntimeException(String.format("Unknown command %s", command));
    }

    void list(Dir dir, ListIterator<String> it) {
      while (it.hasNext()) {
        String s = it.next();
        if (s.startsWith("$")) {
          it.previous();
          break;
        }

        if (s.startsWith("dir ")) {
          String name = s.replace("dir ", "");
          // System.out.println(String.format("  Adding dir %s to %s", name, dir.name));
          dir.contents.put(name, new Dir(name, dir));
        } else {  // file
          String[] parts = s.split(" ");
          long size = Long.parseLong(parts[0]);
          String name = parts[1];
          // System.out.println(String.format("  Adding file %s to %s", name, dir.name));
          dir.contents.put(name, new File(name, size));
        }
      }
    }

    Dir changeDirectory(Dir src, String dst) {
      assert src != null;

      if (dst.equals("..")) {
        return src.parent;
      } else if (dst.equals("/") && src.name.equals("/")) {
        return src;
      }

      if (!src.contents.containsKey(dst)) {
        throw new RuntimeException(String.format("Directory `%s` not found in %s", dst, src.name));
      }

      File file = src.contents.get(dst);

      if (!(file instanceof Dir)) {
        throw new RuntimeException(String.format("%s is of type File, not Dir", dst));
      }

      // System.out.println(String.format("  cd: %s", dst));

      return (Dir)file;
    }
  }

  static class File {
    final String name;
    final long size;

    public File(String name) {
      this(name, 0);
    }

    public File(String name, long size) {
      this.name = name;
      this.size = size;
    }

    public long size() {
      return size;
    }

    @Override
    public String toString() {
      return String.format("%s (file, size=%s)", name, size());
    }
  }

  static class Dir extends File {
    final Dir parent;
    final Map<String, File> contents;

    public Dir(String name, Dir parent) {
      super(name);
      this.parent = parent;
      this.contents = new HashMap<>();
    }

    @Override
    public long size() {
      return contents.values()
          .stream()
          .map(File::size)
          .reduce(0L, Long::sum);
    }

    @Override
    public String toString() {
      return String.format("%s (dir, size=%s)", name, size());
    }
  }
}
