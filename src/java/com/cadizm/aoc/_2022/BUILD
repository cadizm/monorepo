load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "aoc_2022",
    srcs = glob(["*.java"]),
    resource_strip_prefix = "src/resources/aoc/2022",
    resources = ["//src/resources/aoc/2022:input"],
    visibility = ["//visibility:public"],
    deps = [
        "//src/java/com/cadizm/io",
        "//third_party:apache_commons_lang3",
    ],
)
