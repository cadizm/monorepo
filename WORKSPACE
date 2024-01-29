load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

#
# Java Workspace
#

RULES_JVM_EXTERNAL_TAG = "5.3"

RULES_JVM_EXTERNAL_SHA = "d31e369b854322ca5098ea12c69d7175ded971435e55c18dd9dd5f29cc5249ac"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/releases/download/%s/rules_jvm_external-%s.tar.gz" % (RULES_JVM_EXTERNAL_TAG, RULES_JVM_EXTERNAL_TAG),
)

load("@rules_jvm_external//:repositories.bzl", "rules_jvm_external_deps")

rules_jvm_external_deps()

load("@rules_jvm_external//:setup.bzl", "rules_jvm_external_setup")

rules_jvm_external_setup()

load("@rules_jvm_external//:defs.bzl", "maven_install")
load("//third_party:maven_deps.bzl", "MAVEN_ARTIFACTS")

maven_install(
    artifacts = MAVEN_ARTIFACTS,
    fail_if_repin_required = True,
    maven_install_json = "//third_party:maven_install.json",
    repositories = [
        "https://maven.google.com",
        "https://repo1.maven.org/maven2",
    ],
)

load("@maven//:defs.bzl", "pinned_maven_install")

pinned_maven_install()

CONTRIB_RULES_JVM_TAG = "0.9.0"

CONTRIB_RULES_JVM_SHA = "548f0583192ff79c317789b03b882a7be9b1325eb5d3da5d7fdcc4b7ca69d543"

http_archive(
    name = "contrib_rules_jvm",
    sha256 = CONTRIB_RULES_JVM_SHA,
    strip_prefix = "rules_jvm-%s" % CONTRIB_RULES_JVM_TAG,
    url = "https://github.com/bazel-contrib/rules_jvm/archive/refs/tags/v%s.tar.gz" % CONTRIB_RULES_JVM_TAG,
)

load("@contrib_rules_jvm//:repositories.bzl", "contrib_rules_jvm_deps")

contrib_rules_jvm_deps()

load("@contrib_rules_jvm//:setup.bzl", "contrib_rules_jvm_setup")

contrib_rules_jvm_setup()

# https://github.com/salesforce/rules_spring
http_archive(
    name = "rules_spring",
    sha256 = "7bb891ccb2f53ca188a769b3a3777be1c38348e18091afea05320f3003b3e886",
    urls = [
        "https://github.com/salesforce/rules_spring/releases/download/2.3.1/rules-spring-2.3.1.zip",
    ],
)

#
# Python Workspace
#

RULES_PYTHON_TAG = "0.27.0"

RULES_PYTHON_SHA = "9acc0944c94adb23fba1c9988b48768b1bacc6583b52a2586895c5b7491e2e31"

http_archive(
    name = "rules_python",
    sha256 = RULES_PYTHON_SHA,
    strip_prefix = "rules_python-{}".format(RULES_PYTHON_TAG),
    url = "https://github.com/bazelbuild/rules_python/releases/download/{}/rules_python-{}.tar.gz".format(RULES_PYTHON_TAG, RULES_PYTHON_TAG),
)

load("@rules_python//python:repositories.bzl", "py_repositories")

py_repositories()

load("@rules_python//python:repositories.bzl", "python_register_toolchains")

python_register_toolchains(
    name = "python_3_11_6",
    # https://github.com/bazelbuild/rules_python/blob/main/python/versions.bzl
    python_version = "3.11.6",
)

load("@python_3_11_6//:defs.bzl", "interpreter")
load("@rules_python//python:pip.bzl", "pip_parse")

pip_parse(
    name = "pypi_deps",
    python_interpreter_target = interpreter,
    requirements_lock = "//third_party:requirements_lock.txt",
)

load("@pypi_deps//:requirements.bzl", "install_deps")

install_deps()
