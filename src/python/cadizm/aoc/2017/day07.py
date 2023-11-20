# --- Day 7: Recursive Circus ---
#
# Wandering further through the circuits of the computer, you come upon a
# tower of programs that have gotten themselves into a bit of trouble. A
# recursive algorithm has gotten out of hand, and now they're balanced
# precariously in a large tower.
#
# One program at the bottom supports the entire tower. It's holding a large
# disc, and on the disc are balanced several more sub-towers. At the bottom
# of these sub-towers, standing on the bottom disc, are other programs, each
# holding their own disc, and so on. At the very tops of these
# sub-sub-sub-...-towers, many programs stand simply keeping the disc below
# them balanced but with no disc of their own.
#
# You offer to help, but first you need to understand the structure of these
# towers. You ask each program to yell out their name, their weight, and (if
# they're holding a disc) the names of the programs immediately above them
# balancing on that disc. You write this information down (your puzzle
# input). Unfortunately, in their panic, they don't do this in an orderly
# fashion; by the time you're done, you're not sure which program gave which
# information.
#
# For example, if your list is the following:
#
# pbga (66)
# xhth (57)
# ebii (61)
# havc (66)
# ktlj (57)
# fwft (72) -> ktlj, cntj, xhth
# qoyq (66)
# padx (45) -> pbga, havc, qoyq
# tknk (41) -> ugml, padx, fwft
# jptl (61)
# ugml (68) -> gyxo, ebii, jptl
# gyxo (61)
# cntj (57)
# ...then you would be able to recreate the structure of the towers that
# looks like this:
#
#                 gyxo
#               /
#          ugml - ebii
#        /      \
#       |         jptl
#       |
#       |         pbga
#      /        /
# tknk --- padx - havc
#      \        \
#       |         qoyq
#       |
#       |         ktlj
#        \      /
#          fwft - cntj
#               \
#                 xhth
# In this example, tknk is at the bottom of the tower (the bottom program),
# and is holding up ugml, padx, and fwft. Those programs are, in turn,
# holding up other programs; in this example, none of those programs are
# holding up any other programs, and are all the tops of their own towers.
# (The actual tower balancing in front of you is much larger.)
#
# Before you're ready to help them, you need to make sure your information is
# correct. What is the name of the bottom program?

from cadizm.util.resources import open_resource

from collections import defaultdict
import operator
import re


def set_parent(child, node, table):
    if child in table:
        assert table[child] == None
    table[child] = node


def part1():
    with open_resource("aoc/2017/day07.input") as f:
        nodes = set()
        node_parent = {}

        for line in f:
            line = line.strip()
            match = re.match("^(\w+)\s+\((\d+)\)(?:\s+\->(.*?))?$", line)
            if not match:
                raise Exception("No match found for %s" % line)

            node, weight = match.group(1), match.group(2)
            nodes.add(node)

            if match.group(3):
                children = [c.strip() for c in match.group(3).split(",")]
                for child in children:
                    nodes.add(child)
                    set_parent(child, node, node_parent)

            elif node not in node_parent:
                node_parent[node] = None

        return (nodes - set(node_parent.keys())).pop()


print(part1())


# --- Part Two ---
# The programs explain the situation: they can't get down. Rather, they could
# get down, if they weren't expending all of their energy trying to keep the
# tower balanced. Apparently, one program has the wrong weight, and until
# it's fixed, they're stuck here.
#
# For any program holding a disc, each program standing on that disc forms a
# sub-tower. Each of those sub-towers are supposed to be the same weight, or
# the disc itself isn't balanced. The weight of a tower is the sum of the
# weights of the programs in that tower.
#
# In the example above, this means that for ugml's disc to be balanced, gyxo,
# ebii, and jptl must all have the same weight, and they do: 61.
#
# However, for tknk to be balanced, each of the programs standing on its disc
# and all programs above it must each match. This means that the following
# sums must all be the same:
#
# ugml + (gyxo + ebii + jptl) = 68 + (61 + 61 + 61) = 251
# padx + (pbga + havc + qoyq) = 45 + (66 + 66 + 66) = 243
# fwft + (ktlj + cntj + xhth) = 72 + (57 + 57 + 57) = 243
# As you can see, tknk's disc is unbalanced: ugml's stack is heavier than the
# other two. Even though the nodes above ugml are balanced, ugml itself is
# too heavy: it needs to be 8 units lighter for its stack to weigh 243 and
# keep the towers balanced. If this change were made, its weight would be 60.
#
# Given that exactly one program is the wrong weight, what would its weight
# need to be to balance the entire tower?


def weight_sum(node, node_children, node_weight):
    if node not in node_children:
        return node_weight[node]

    return node_weight[node] + sum(
        [weight_sum(c, node_children, node_weight) for c in node_children[node]]
    )


def check_weights(node, node_children, node_weight, res):
    if node not in node_children:
        return

    weights = set(
        [weight_sum(c, node_children, node_weight) for c in node_children[node]]
    )

    if len(weights) != 1:
        weight_child = defaultdict(list)
        for c in node_children[node]:
            weight_child[weight_sum(c, node_children, node_weight)].append(c)

        for weight, children in weight_child.items():
            if len(children) == 1:
                bad_weight = weight
                target_weight = (weights - set([bad_weight])).pop()
                bad_node = weight_child[bad_weight].pop()
                break
        else:
            raise Exception("Invalid state")

        op = operator.sub if target_weight < bad_weight else operator.add
        res.append(op(node_weight[bad_node], abs(target_weight - bad_weight)))

    for c in node_children[node]:
        check_weights(c, node_children, node_weight, res)


def part2():
    with open_resource("aoc/2017/day07.input") as f:
        nodes = set()
        node_parent = {}
        node_children = {}
        node_weight = {}

        for line in f:
            line = line.strip()
            match = re.match("^(\w+)\s+\((\d+)\)(?:\s+\->(.*?))?$", line)
            if not match:
                raise Exception("No match found for %s" % line)

            node, weight = match.group(1), int(match.group(2))
            nodes.add(node)
            node_weight[node] = weight

            if match.group(3):
                children = [c.strip() for c in match.group(3).split(",")]
                node_children[node] = children
                for child in children:
                    nodes.add(child)
                    set_parent(child, node, node_parent)

            elif node not in node_parent:
                node_parent[node] = None

        root = (nodes - set(node_parent.keys())).pop()

        res = []  # chain of bad weights rippling from right-to-left (child-to-parent)
        check_weights(root, node_children, node_weight, res)

        return res[-1]


print(part2())
