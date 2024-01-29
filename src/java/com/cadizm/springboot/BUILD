load("@rules_java//java:defs.bzl", "java_library")
load("@rules_spring//springboot:springboot.bzl", "springboot")

java_library(
    name = "helloworld_lib",
    srcs = glob(["*.java"]),
    resource_strip_prefix = "src/resources/springboot",
    resources = ["//src/resources/springboot:resources"],
    visibility = ["//visibility:public"],
    deps = [
        "//third_party:javax_annotation_api",
        "//third_party:rules_spring",
        "//third_party:spring_boot_loader",
        "//third_party:spring_boot_starter",
        "//third_party:spring_webmvc",
    ],
)

# https://github.com/salesforce/rules_spring/blob/main/springboot/README.md
springboot(
    name = "helloworld",
    boot_app_class = "com.cadizm.springboot.SampleMain",
    boot_launcher_class = "org.springframework.boot.loader.launch.JarLauncher",
    deps_exclude_paths = [
        "tomcat",  # Use Jetty instead of Tomcat
    ],
    java_library = ":helloworld_lib",
)
