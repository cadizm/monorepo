# Retrieve an authentication token and authenticate Docker client with ECR

```shell
$ aws ecr-public get-login-password --region us-east-1 | docker login --username AWS --password-stdin public.ecr.aws/z8h5c1m3
```

# List ECR spring-boot-helloworld images

```shell
$ docker image ls public.ecr.aws/z8h5c1m3/cadizm/spring-boot-helloworld
```
