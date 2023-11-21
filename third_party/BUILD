load("@rules_java//java:defs.bzl", "java_library")

exports_files(
    ["requirements_lock.txt"],
)

java_library(
    name = "google_guava",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:com_google_guava_guava",
    ],
)

java_library(
    name = "hamcrest",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:org_hamcrest_hamcrest",
    ],
)

java_library(
    name = "junit_jupiter",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:org_junit_jupiter_junit_jupiter_api",
        "@maven//:org_junit_jupiter_junit_jupiter_params",
    ],
)

java_library(
    name = "apache_commons_lang3",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:org_apache_commons_commons_lang3",
    ],
)
