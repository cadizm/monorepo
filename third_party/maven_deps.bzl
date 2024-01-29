JUNIT_JUPITER_VERSION = "5.10.1"
JUNIT_PLATFORM_VERSION = "1.10.1"

SPRING_BOOT_VERSION = "3.2.2"

# When adding a new artifact, make sure to repin maven_install.json by running:
#   $ REPIN=1 bazel run @unpinned_maven//:pin
#
# See https://github.com/bazelbuild/rules_jvm_external#requiring-lock-file-repinning-when-the-list-of-artifacts-changes
MAVEN_ARTIFACTS = [
    "javax.annotation:javax.annotation-api:1.3.2",
    "com.google.guava:guava:32.1.3-jre",
    "com.fasterxml.jackson.core:jackson-core:2.15.3",
    "org.apache.commons:commons-lang3:3.13.0",
    "org.junit.platform:junit-platform-launcher:%s" % JUNIT_PLATFORM_VERSION,
    "org.junit.platform:junit-platform-reporting:%s" % JUNIT_PLATFORM_VERSION,
    "org.junit.jupiter:junit-jupiter-api:%s" % JUNIT_JUPITER_VERSION,
    "org.junit.jupiter:junit-jupiter-params:%s" % JUNIT_JUPITER_VERSION,
    "org.junit.jupiter:junit-jupiter-engine:%s" % JUNIT_JUPITER_VERSION,
    "org.hamcrest:hamcrest:2.2",
    "org.springframework.boot:spring-boot-starter:%s" % SPRING_BOOT_VERSION,
    "org.springframework.boot:spring-boot-starter-actuator:%s" % SPRING_BOOT_VERSION,
    "org.springframework.boot:spring-boot-starter-jetty:%s" % SPRING_BOOT_VERSION,
    "org.springframework.boot:spring-boot-starter-web:%s" % SPRING_BOOT_VERSION,
    "org.springframework.boot:spring-boot-loader:%s" % SPRING_BOOT_VERSION,
    "org.springframework.boot:spring-boot-loader-tools:%s" % SPRING_BOOT_VERSION,
    "org.springframework:spring-webmvc:6.1.3",
]
