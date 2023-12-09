load("@rules_java//java:defs.bzl", "java_library")

java_library(
    name = "aoc_2023",
    srcs = glob(["*.java"]),
    resource_strip_prefix = "src/resources/aoc/2023",
    resources = ["//src/resources/aoc/2023:input"],
    visibility = ["//visibility:public"],
    deps = [
        "//src/java/com/cadizm/io",
        "//src/java/com/cadizm/math",
        "//src/java/com/cadizm/matrix",
        "//third_party:apache_commons_lang3",
        "//third_party:google_guava",
    ],
)
