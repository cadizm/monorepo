# --- Day 14: Disk Defragmentation ---
# Suddenly, a scheduled job activates the system's disk defragmenter. Were the
# situation different, you might sit and watch it for a while, but today, you
# just don't have that kind of time. It's soaking up valuable system resources
# that are needed elsewhere, and so the only option is to help it finish its
# task as soon as possible.
#
# The disk in question consists of a 128x128 grid; each square of the grid is
# either free or used. On this disk, the state of the grid is tracked by the
# bits in a sequence of knot hashes.
#
# A total of 128 knot hashes are calculated, each corresponding to a single row
# in the grid; each hash contains 128 bits which correspond to individual grid
# squares. Each bit of a hash indicates whether that square is free (0) or used
# (1).
#
# The hash inputs are a key string (your puzzle input), a dash, and a number
# from 0 to 127 corresponding to the row. For example, if your key string were
# flqrgnkx, then the first row would be given by the bits of the knot hash of
# flqrgnkx-0, the second row from the bits of the knot hash of flqrgnkx-1, and
# so on until the last row, flqrgnkx-127.
#
# The output of a knot hash is traditionally represented by 32 hexadecimal
# digits; each of these digits correspond to 4 bits, for a total of 4 * 32 =
# 128 bits. To convert to bits, turn each hexadecimal digit to its equivalent
# binary value, high-bit first: 0 becomes 0000, 1 becomes 0001, e becomes 1110,
# f becomes 1111, and so on; a hash that begins with a0c2017... in hexadecimal
# would begin with 10100000110000100000000101110000... in binary.
#
# Continuing this process, the first 8 rows and columns for key flqrgnkx appear
# as follows, using # to denote used squares, and . to denote free ones:
#
# ##.#.#..-->
# .#.#.#.#
# ....#.#.
# #.#.##.#
# .##.#...
# ##..#..#
# .#...#..
# ##.#.##.-->
# |      |
# V      V
# In this example, 8108 squares are used across the entire 128x128 grid.
#
# Given your actual key string, how many squares are used?

from functools import reduce

from cadizm.util.resources import open_resource


def circular_reverse(L, index, length):
    N = len(L)

    if index + length <= len(L):
        M = L[index : index + length]
    else:
        M = L[index:] + L[: (index + length) % N]

    for i, v in enumerate(reversed(M)):
        L[(index + i) % N] = v

    return L


def asciify(s):
    return [ord(c) for c in s]


def knot_hash_round(L, lengths, pos, skip):
    for v in lengths:
        L = circular_reverse(L, pos, v)
        pos = (pos + v + skip) % len(L)
        skip += 1

    return pos, skip


def knot_hash(s):
    L = list(range(256))
    lengths = asciify(s) + [17, 31, 73, 47, 23]
    pos, skip = 0, 0

    for _ in range(64):
        pos, skip = knot_hash_round(L, lengths, pos, skip)

    M = [reduce(lambda x, y: x ^ y, L[16 * i : 16 * i + 16]) for i in range(256 // 16)]

    return "".join(["%02x" % m for m in M])


def num_set_bits(d):
    count = 0

    while d:
        if d & 0x1:
            count += 1
        d >>= 1

    return count


def part1():
    key = open_resource("aoc/2017/day14.input").read().strip()

    hashes = []
    for i in range(128):
        hashes.append(knot_hash("%s-%d" % (key, i)))

    return sum([num_set_bits(int(h, base=16)) for h in hashes])


print(part1())
