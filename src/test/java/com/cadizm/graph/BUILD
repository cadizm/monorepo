load("@contrib_rules_jvm//java:defs.bzl", "JUNIT5_DEPS", "java_test_suite")

java_test_suite(
    name = "test",
    srcs = glob(["*.java"]),
    runner = "junit5",
    test_suffixes = ["Test.java"],
    runtime_deps = JUNIT5_DEPS,
    deps = [
        "//src/main/java/com/cadizm/graph",
        "//third_party:hamcrest",
        "//third_party:junit_jupiter",
    ],
)
