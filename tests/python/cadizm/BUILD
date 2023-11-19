load("@rules_python//python:defs.bzl", "py_library", "py_test")

py_library(
    name = "__init__",
    srcs = ["__init__.py"],
    imports = [".."],
)

py_test(
    name = "test_hello",
    srcs = ["test_hello.py"],
    deps = [
        "//src/python/cadizm:__init__",
        "//src/python/cadizm:hello",
    ],
)
