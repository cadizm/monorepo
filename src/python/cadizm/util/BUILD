load("@rules_python//python:defs.bzl", "py_library")
load("@pypi_deps//:requirements.bzl", "requirement")

py_library(
    name = "resources",
    srcs = ["resources.py"],
    visibility = ["//visibility:public"],
    deps = [
        "//src/python/cadizm",
        requirement("bazel-runfiles"),
    ],
)
