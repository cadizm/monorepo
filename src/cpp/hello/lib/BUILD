load("@rules_cc//cc:defs.bzl", "cc_library")

cc_library(
    name = "hello-time",
    srcs = ["hello-time.cc"],
    hdrs = ["hello-time.h"],
    copts = ["-Isrc/cpp"],
    visibility = ["//src/cpp/hello/main:__pkg__"],
)
