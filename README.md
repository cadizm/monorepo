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
3. Select `Import project view file` and choose [project/intellij/java/java.bazelproject](./project/intellij/java/java.bazelproject)
4. For `Project data directory` choose [project/intellij/java](./project/intellij/java)
5. For `Project name` choose something like `monorepo-java`
4. `Create`

### Python

1. `Import Bazel Project`
2. Select repository root as Bazel workspace
3. Select `Import project view file` and choose [project/intellij/python/python.bazelproject](./project/intellij/python/python.bazelproject)
4. For `Project data directory` choose [project/intellij/python](./project/intellij/python)
5. For `Project name` choose something like `monorepo-python`
4. `Create`
