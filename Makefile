# FunPokeDex Makefile
# Author: Sara Bellatorre

IMAGE_NAME = funpokedex
IMAGE_TAG = latest
CONTAINER_NAME = funpokedex
PORT = 8080

.PHONY: help build run start stop restart logs logs-f clean

help:
	@echo "FunPokeDex - Available Make Targets:"
	@echo "  build      - Build the Docker image"
	@echo "  run        - Run the container"
	@echo "  start      - Build and run"
	@echo "  stop       - Stop the container"
	@echo "  restart    - Restart container"
	@echo "  logs       - View logs"
	@echo "  logs-f     - Follow logs"
	@echo "  clean      - Remove container and image"

build:
	docker build -t $(IMAGE_NAME):$(IMAGE_TAG) .

run:
	docker run -d -p $(PORT):$(PORT) --name $(CONTAINER_NAME) $(IMAGE_NAME):$(IMAGE_TAG)

start: build run
	@echo "App running at http://localhost:$(PORT)"

stop:
	-docker stop $(CONTAINER_NAME)
	-docker rm $(CONTAINER_NAME)

restart: stop start

logs:
	docker logs $(CONTAINER_NAME)

logs-f:
	docker logs -f $(CONTAINER_NAME)

clean: stop
	-docker rmi $(IMAGE_NAME):$(IMAGE_TAG)
