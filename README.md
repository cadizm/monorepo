# `cadizm` monorepo

# Usage

## Query targets

```shell
$ bazel query //...
```

## Build and test targets

```shell
$ bazel build //...
$ bazel test //...
```

## Run a target

```shell
$ bazel run //src/python/cadizm:hello
```

# Setup

IDE support is provided for [IntelliJ](https://www.jetbrains.com/idea/)

## IntelliJ

### Java

1. `Import Bazel Project`
2. Select repository root as Bazel workspace
3. Select `Import project view file` and choose [project/bazel/java/java.bazelproject](./project/bazel/java/java.bazelproject)
4. For `Project data directory` choose [project/bazel/java](./project/bazel/java)
5. For `Project name` choose something like `monorepo-java`
4. `Create`

### Python

1. `Import Bazel Project`
2. Select repository root as Bazel workspace
3. Select `Import project view file` and choose [project/bazel/python/python.bazelproject](./project/bazel/python/python.bazelproject)
4. For `Project data directory` choose [project/bazel/python](./project/bazel/python)
5. For `Project name` choose something like `monorepo-python`
4. `Create`
