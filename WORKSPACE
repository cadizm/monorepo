load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

RULES_JVM_EXTERNAL_TAG = "5.3"
RULES_JVM_EXTERNAL_SHA ="d31e369b854322ca5098ea12c69d7175ded971435e55c18dd9dd5f29cc5249ac"

http_archive(
    name = "rules_jvm_external",
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    sha256 = RULES_JVM_EXTERNAL_SHA,
    url = "https://github.com/bazelbuild/rules_jvm_external/releases/download/%s/rules_jvm_external-%s.tar.gz" % (RULES_JVM_EXTERNAL_TAG, RULES_JVM_EXTERNAL_TAG)
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")

JUNIT_JUPITER_VERSION = "5.10.1"
JUNIT_PLATFORM_VERSION = "1.10.1"

maven_install(
    artifacts = [
        "com.google.guava:guava:32.1.3-jre",
        "org.hamcrest:hamcrest:2.2",
        "org.junit.platform:junit-platform-launcher:%s" % JUNIT_PLATFORM_VERSION,
        "org.junit.platform:junit-platform-reporting:%s" % JUNIT_PLATFORM_VERSION,
        "org.junit.jupiter:junit-jupiter-api:%s" % JUNIT_JUPITER_VERSION,
        "org.junit.jupiter:junit-jupiter-params:%s" % JUNIT_JUPITER_VERSION,
        "org.junit.jupiter:junit-jupiter-engine:%s" % JUNIT_JUPITER_VERSION,
    ],
    repositories = [
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)

CONTRIB_RULES_JVM_VERSION = "0.9.0"
CONTRIB_RULES_JVM_SHA = "548f0583192ff79c317789b03b882a7be9b1325eb5d3da5d7fdcc4b7ca69d543"

http_archive(
    name = "contrib_rules_jvm",
    sha256 = CONTRIB_RULES_JVM_SHA,
    strip_prefix = "rules_jvm-%s" % CONTRIB_RULES_JVM_VERSION,
    url = "https://github.com/bazel-contrib/rules_jvm/archive/refs/tags/v%s.tar.gz" % CONTRIB_RULES_JVM_VERSION,
)

load("@contrib_rules_jvm//:repositories.bzl", "contrib_rules_jvm_deps")

contrib_rules_jvm_deps()

load("@contrib_rules_jvm//:setup.bzl", "contrib_rules_jvm_setup")

contrib_rules_jvm_setup()
