# --- Day 11: Hex Ed ---
#
# Crossing the bridge, you've barely reached the other side of the stream
# when a program comes up to you, clearly in distress. "It's my child
# process," she says, "he's gotten lost in an infinite grid!"
#
# Fortunately for her, you have plenty of experience with infinite grids.
#
# Unfortunately for you, it's a hex grid.
#
# The hexagons ("hexes") in this grid are aligned such that adjacent hexes
# can be found to the north, northeast, southeast, south, southwest, and
# northwest:
#
#   \ n  /
# nw +--+ ne
#   /    \
# -+      +-
#   \    /
# sw +--+ se
#   / s  \
# You have the path the child process took. Starting where he started, you
# need to determine the fewest number of steps required to reach him. (A
# "step" means to move from the hex you are in to any adjacent hex.)
#
# For example:
#
# ne,ne,ne is 3 steps away.
# ne,ne,sw,sw is 0 steps away (back where you started).
# ne,ne,s,s is 2 steps away (se,se).
# se,sw,se,sw,sw is 3 steps away (s,s,sw).

from cadizm.util.resources import open_resource


def part1():
    with open_resource("aoc/2017/day11.input") as f:
        steps = {
            "n": 0.0,
            "s": 0.0,
            "e": 0.0,
            "w": 0.0,
        }

        path = [p.strip() for p in f.read().split(",")]

        for step in path:
            if step in ["n", "s"]:
                steps[step] += 1
            else:
                for s in list(step):
                    steps[s] += 0.5

        return abs(steps["n"] - steps["s"]) + abs(steps["e"] - steps["w"])


print(part1())


# --- Part Two ---
# How many steps away is the furthest he ever got from his starting position?


def part2():
    with open_resource("aoc/2017/day11.input") as f:
        steps = {
            "n": 0.0,
            "s": 0.0,
            "e": 0.0,
            "w": 0.0,
        }

        max_steps_ever = 0
        path = [p.strip() for p in f.read().split(",")]

        for step in path:
            if step in ["n", "s"]:
                steps[step] += 1
            else:
                for s in list(step):
                    steps[s] += 0.5

            max_steps_ever = max(
                max_steps_ever,
                abs(steps["n"] - steps["s"]) + abs(steps["e"] - steps["w"]),
            )

        return max_steps_ever


print(part2())
