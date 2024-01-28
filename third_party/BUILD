load("@rules_java//java:defs.bzl", "java_library")

exports_files([
      "maven_deps.bzl",
      "maven_install.json",
      "requirements_lock.txt",
    ],
)

java_library(
    name = "javax_annotation_api",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:javax_annotation_javax_annotation_api",
    ],
)

java_library(
    name = "google_guava",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:com_google_guava_guava",
    ],
)

java_library(
    name = "jackson_core",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:com_fasterxml_jackson_core_jackson_core",
    ],
)

java_library(
    name = "apache_commons_lang3",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:org_apache_commons_commons_lang3",
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
    name = "hamcrest",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:org_hamcrest_hamcrest",
    ],
)

# Note: non-maven dep (defined in WORKSPACE)
java_library(
    name = "rules_spring",
    visibility = ["//visibility:public"],
    exports = [
        "@rules_spring//springboot/import_bundles:springboot_required_deps",
    ],
)

java_library(
    name = "spring_boot_starter",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:org_springframework_boot_spring_boot_starter",
        "@maven//:org_springframework_boot_spring_boot_starter_actuator",
        "@maven//:org_springframework_boot_spring_boot_starter_jetty",
        "@maven//:org_springframework_boot_spring_boot_starter_web",
    ],
)

java_library(
    name = "spring_boot_loader",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:org_springframework_boot_spring_boot_loader",
        "@maven//:org_springframework_boot_spring_boot_loader_tools",
    ],
)

java_library(
    name = "spring_webmvc",
    visibility = ["//visibility:public"],
    exports = [
        "@maven//:org_springframework_spring_webmvc",
    ],
)
