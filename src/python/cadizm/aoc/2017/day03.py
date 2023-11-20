# --- Day 3: Spiral Memory ---
#
# You come across an experimental new kind of memory stored on an infinite
# two-dimensional grid.
#
# Each square on the grid is allocated in a spiral pattern starting at a
# location marked 1 and then counting up while spiraling outward. For
# example, the first few squares are allocated like this:
#
# 17  16  15  14  13
# 18   5   4   3  12
# 19   6   1   2  11
# 20   7   8   9  10
# 21  22  23---> ...
#
# While this is very space-efficient (no squares are skipped), requested data
# must be carried back to square 1 (the location of the only access port for
# this memory system) by programs that can only move up, down, left, or
# right. They always take the shortest path: the Manhattan Distance between
# the location of the data and square 1.
#
# For example:
#
# Data from square 1 is carried 0 steps, since it's at the access port.
# Data from square 12 is carried 3 steps, such as: down, left, left.
# Data from square 23 is carried only 2 steps: up twice.
# Data from square 1024 must be carried 31 steps.
# How many steps are required to carry the data from the square identified in
# your puzzle input all the way to the access port?

from cadizm.util.resources import open_resource


def dimension(step=1):
    "Dimension of matrix for step n"
    return step * 2 + 1


def center(dim):
    return (dim - 1) // 2


def right_corner(step=1):
    return dimension(step=step) * dimension(step=step)


def bottom_row(step=1):
    "Left-to-right order"
    corner = right_corner(step)
    return list(reversed([corner - i for i in range(dimension(step))]))


def fill_bottom(M, step=1):
    row = M[-1]
    for i, v in enumerate(bottom_row(step=step)):
        row[i] = v


def left_column(step=1):
    "Top-to-bottom order"
    bottom = bottom_row(step)
    corner = bottom[0]
    return list(reversed([corner - i for i in range(dimension(step))]))


def fill_left(M, step=1):
    for i, v in enumerate(left_column(step=step)):
        M[i][0] = v


def top_row(step=1):
    "Left-to-right order"
    left = left_column(step=step)
    corner = left[0]
    return [corner - i for i in range(dimension(step))]


def fill_top(M, step=1):
    row = M[0]
    for i, v in enumerate(top_row(step=step)):
        row[i] = v


def right_column(step=1):
    "Top-to-bottom order"
    top = top_row(step)
    corner = top[-1]
    return [corner - i for i in range(dimension(step) - 1)] + [right_corner(step=step)]


def fill_right(M, step=1):
    for i, v in enumerate(right_column(step=step)):
        M[i][-1] = v


def expand(M):
    n = len(M)

    for row in M:
        row.insert(0, None)
        row.append(None)

    M.insert(0, [None for _ in range(n + 2)])
    M.append([None for _ in range(n + 2)])


def matrix(src_square):
    # dim=3
    M = [
        [5, 4, 3],
        [6, 1, 2],
        [7, 8, 9],
    ]

    funcs = [fill_left, fill_right, fill_top, fill_bottom]

    while M[-1][-1] < src_square:
        expand(M)
        for f in funcs:
            f(M, step=center(len(M)))

    return M


def manhattan_distance(M, src_square):
    for i, row in enumerate(M):
        for j, col in enumerate(row):
            if M[i][j] == src_square:
                c = center(len(M))
                return abs(c - i) + abs(c - j)


def dump(M):
    for row in M:
        print("[", end="")
        for elem in row:
            print("%7s" % elem, end="")
        print("]")


def part1():
    with open_resource("aoc/2017/day03.input") as f:
        src_square = int(f.read().strip())
        return manhattan_distance(matrix(src_square), src_square)


print(part1())


# --- Part Two ---
#
# As a stress test on the system, the programs here clear the grid and then
# store the value 1 in square 1. Then, in the same allocation order as shown
# above, they store the sum of the values in all adjacent squares, including
# diagonals.
#
# So, the first few squares' values are chosen as follows:
#
# Square 1 starts with the value 1.
# Square 2 has only one adjacent filled square (with value 1), so it also
# stores 1.
# Square 3 has both of the above squares as neighbors and stores the sum of
# their values, 2.
# Square 4 has all three of the aforementioned squares as neighbors and
# stores the sum of their values, 4.
# Square 5 only has the first and fourth squares as neighbors, so it gets the
# value 5.
# Once a square is written, its value does not change. Therefore, the first
# few squares would receive the following values:
#
# 147  142  133  122   59
# 304    5    4    2   57
# 330   10    1    1   54
# 351   11   23   25   26
# 362  747  806--->   ...
#
# What is the first value written that is larger than your puzzle input?


def spiral(M):
    """
    Yield (row, col) indices for spiral iteration of M

    https://stackoverflow.com/questions/398299/looping-in-a-spiral
    """
    x, dx = 0, 0
    y, dy = 0, -1

    N = len(M)
    for i in range(N**2):
        yield center(N) - y, center(N) + x

        if x == y or (x < 0 and x == -y) or (x > 0 and x == 1 - y):
            dx, dy = -dy, dx

        x += dx
        y += dy


def adjacent(M, row, col):
    indices = [(1, 0), (-1, 0), (0, 1), (0, -1)] + [(1, -1), (1, 1), (-1, -1), (-1, 1)]

    res = []
    for dy, dx in indices:
        if 0 <= row + dy < len(M) and 0 <= col + dx < len(M):
            if M[row + dy][col + dx]:
                res.append(M[row + dy][col + dx])

    return res


def part2():
    with open_resource("aoc/2017/day03.input") as f:
        src_square = int(f.read().strip())
        M = [[1]]

        while True:
            expand(M)
            for row, col in spiral(M):
                if not M[row][col]:
                    M[row][col] = sum(adjacent(M, row, col))
                    if M[row][col] > src_square:
                        return M[row][col]


print(part2())
