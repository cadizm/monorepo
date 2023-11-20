import runfiles

_runfiles = runfiles.Create()


def open_resource(path):
    """
    Return file object for resource specified by path.

    Paths are relative to //src/resources, for example:
      open("aoc/2017/day01.input")

    See https://pypi.org/project/bazel-runfiles/ for more info.
    """
    return open(_runfiles.Rlocation(f"__main__/src/resources/{path}"))
