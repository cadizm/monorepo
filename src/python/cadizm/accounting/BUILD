load("@rules_python//python:defs.bzl", "py_binary", "py_library")

py_library(
    name = "amortization",
    srcs = ["amortization.py"],
    visibility = ["//visibility:public"],
    deps = ["//src/python/cadizm"],
)

py_binary(
    name = "amortization_bin",
    srcs = ["amortization.py"],
    main = "amortization.py",
    deps = [":amortization"],
)
