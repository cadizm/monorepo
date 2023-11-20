# --- Day 8: I Heard You Like Registers ---
#
# You receive a signal directly from the CPU. Because of your recent
# assistance with jump instructions, it would like you to compute the result
# of a series of unusual register instructions.
#
# Each instruction consists of several parts: the register to modify, whether
# to increase or decrease that register's value, the amount by which to
# increase or decrease it, and a condition. If the condition fails, skip the
# instruction without modifying the register. The registers all start at 0.
# The instructions look like this:
#
# b inc 5 if a > 1
# a inc 1 if b < 5
# c dec -10 if a >= 1
# c inc -20 if c == 10
# These instructions would be processed as follows:
#
# Because a starts at 0, it is not greater than 1, and so b is not modified.
# a is increased by 1 (to 1) because b is less than 5 (it is 0).
# c is decreased by -10 (to 10) because a is now greater than or equal to 1
# (it is 1).
# c is increased by -20 (to -10) because c is equal to 10.
# After this process, the largest value in any register is 1.
#
# You might also encounter <= (less than or equal to) or != (not equal to).
# However, the CPU doesn't have the bandwidth to tell you what all the
# registers are named, and leaves that to you to determine.
#
# What is the largest value in any register after completing the instructions
# in your puzzle input?


from cadizm.util.resources import open_resource
import operator
import re
from collections import defaultdict

op_map = {
    "inc": operator.add,
    "dec": operator.sub,
}


def part1():
    with open_resource("aoc/2017/day08.input") as f:
        reg = defaultdict(int)

        for line in f:
            m1 = re.match(
                r"^(?P<reg>\w+)\s+(?P<op>inc|dec)\s+(?P<arg>\-?\d+)\s+(?P<cond>.*?)$",
                line.strip(),
            )
            m2 = re.match(r"if\s+(?P<cond_reg>\w+)\s+(?P<rel>.*?)$", m1.group("cond"))

            if eval("%d %s" % (reg[m2.group("cond_reg")], m2.group("rel"))):
                reg[m1.group("reg")] = op_map[m1.group("op")](
                    reg[m1.group("reg")], int(m1.group("arg"))
                )

        return max(reg.values())


print(part1())


# --- Part Two ---
# To be safe, the CPU also needs to know the highest value held in any
# register during this process so that it can decide how much memory to
# allocate to these operations. For example, in the above instructions, the
# highest value ever held was 10 (in register c after the third instruction
# was evaluated).


def part2():
    with open_resource("aoc/2017/day08.input") as f:
        reg = defaultdict(int)
        max_ever = 0

        for line in f:
            m1 = re.match(
                r"^(?P<reg>\w+)\s+(?P<op>inc|dec)\s+(?P<arg>\-?\d+)\s+(?P<cond>.*?)$",
                line.strip(),
            )
            m2 = re.match(r"if\s+(?P<cond_reg>\w+)\s+(?P<rel>.*?)$", m1.group("cond"))

            if eval("%d %s" % (reg[m2.group("cond_reg")], m2.group("rel"))):
                reg[m1.group("reg")] = op_map[m1.group("op")](
                    reg[m1.group("reg")], int(m1.group("arg"))
                )
                max_ever = max(max_ever, max(reg.values()))

        return max_ever


print(part2())
