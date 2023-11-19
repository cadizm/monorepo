load("@rules_python//python:defs.bzl", "py_binary", "py_library")
load("@pypi_deps//:requirements.bzl", "requirement")

py_library(
    name = "dynamodb",
    srcs = [
        "__init__.py",
        "config.py",
        "create_tables.py",
        "delete_tables.py",
        "dump_table_items.py",
        "list_tables.py",
        "schema.py",
    ],
    visibility = ["//visibility:public"],
    deps = [
        "//src/python/cadizm:__init__",
        requirement("boto3"),
    ],
)
