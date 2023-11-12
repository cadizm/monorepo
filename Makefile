all:
	@echo "See Makefile for valid targets"

.PHONY: maven-repin
maven-repin:
	REPIN=1 bazel run @unpinned_maven//:pin
