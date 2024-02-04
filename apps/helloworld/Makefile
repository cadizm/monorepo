REV := $(shell git rev-parse --short HEAD)
CONTEXT := ../..

REGISTRY := public.ecr.aws/z8h5c1m3
REPOSITORY := cadizm/spring-boot-helloworld

build:
	docker build -f Dockerfile --tag $(REPOSITORY):$(REV) --tag $(REPOSITORY):latest $(CONTEXT)

docker-run: build
	docker run --rm -p 8080:8080 $(REPOSITORY):$(REV)

tag: build
	docker tag $(REPOSITORY):$(REV) $(REGISTRY)/$(REPOSITORY):$(REV)
	docker tag $(REPOSITORY):latest $(REGISTRY)/$(REPOSITORY):latest

push: tag
	docker push --all-tags $(REGISTRY)/$(REPOSITORY)
