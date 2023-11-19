load("@rules_python//python:defs.bzl", "py_binary", "py_library")
load("@pypi_deps//:requirements.bzl", "requirement")

py_library(
    name = "__init__",
    srcs = ["__init__.py"],
    imports = [".."],
    visibility = ["//visibility:public"],
)

py_library(
    name = "amortization",
    srcs = ["amortization.py"],
    visibility = ["//visibility:public"],
    deps = ["//src/python/cadizm:__init__"],
)

py_binary(
    name = "amortization_bin",
    srcs = ["amortization.py"],
    main = "amortization.py",
    deps = [":amortization"],
)
