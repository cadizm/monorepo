all:
	@echo "See Makefile for valid targets"

.PHONY: repin-maven
repin-maven:
	REPIN=1 bazel run @unpinned_maven//:pin
