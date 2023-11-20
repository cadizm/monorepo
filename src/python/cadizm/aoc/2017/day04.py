# --- Day 4: High-Entropy Passphrases ---
#
# A new system policy has been put in place that requires all accounts to use
# a passphrase instead of simply a password. A passphrase consists of a
# series of words (lowercase letters) separated by spaces.
#
# To ensure security, a valid passphrase must contain no duplicate words.
#
# For example:
#
# aa bb cc dd ee is valid.
# aa bb cc dd aa is not valid - the word aa appears more than once.
# aa bb cc dd aaa is valid - aa and aaa count as different words.
# The system's full passphrase list is available as your puzzle input. How
# many passphrases are valid?

from cadizm.util.resources import open_resource

from collections import defaultdict


def part1():
    with open_resource("aoc/2017/day04.input") as f:
        res = 0

        for line in [line.strip() for line in f]:
            word_count = defaultdict(int)
            for word in line.split():
                word_count[word] += 1
            if all([count == 1 for count in word_count.values()]):
                res += 1

        return res


print(part1())


# --- Part Two ---
#
# For added security, yet another system policy has been put in place. Now, a
# valid passphrase must contain no two words that are anagrams of each other
# - that is, a passphrase is invalid if any word's letters can be rearranged
# to form any other word in the passphrase.
#
# For example:
#
# abcde fghij is a valid passphrase.
# abcde xyz ecdab is not valid - the letters from the third word can be
# rearranged to form the first word.
# a ab abc abd abf abj is a valid passphrase, because all letters need to be
# used when forming another word.
# iiii oiii ooii oooi oooo is valid.
# oiii ioii iioi iiio is not valid - any of these words can be rearranged to
# form any other word.
#
# Under this new system policy, how many passphrases are valid?


def dictify(word):
    D = defaultdict(int)

    for c in word:
        D[c] += 1

    return D


def part2():
    with open_resource("aoc/2017/day04.input") as f:
        res = 0

        for line in [line.strip() for line in f.readlines()]:
            L = []
            for word in line.split():
                word = dictify(word)
                if word in L:
                    break
                L.append(word)
            else:
                res += 1

        return res


print(part2())
