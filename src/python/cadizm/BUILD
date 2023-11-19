load("@rules_python//python:defs.bzl", "py_binary", "py_library")
load("@pypi_deps//:requirements.bzl", "requirement")

py_library(
    name = "__init__",
    srcs = ["__init__.py"],
    imports = [".."],
    visibility = ["//visibility:public"],
)

py_library(
    name = "hello",
    srcs = ["hello.py"],
    visibility = ["//visibility:public"],
    deps = [
        "//src/python/cadizm:__init__",
        "//src/python/cadizm/foo:__init__",
        requirement("ipdb"),
    ],
)

py_binary(
    name = "hello_bin",
    srcs = ["hello.py"],
    main = "hello.py",
    deps = [":hello"],
)
