IMAGE_ID := "myownradio/radioteria-base"
CONTAINER_ID := "radioteria_base"

docker-build:
	docker build -t $(IMAGE_ID) .

docker-push:
	docker push $(IMAGE_ID)

docker-test:
	docker create --name $(CONTAINER_ID) --rm -w /app $(IMAGE_ID) make test
	docker cp . $(CONTAINER_ID):/app
	docker start --attach $(CONTAINER_ID)

docker-run:
	docker create --name $(CONTAINER_ID) --rm -w /app -p 8080:8080 $(IMAGE_ID) make run
	docker cp . $(CONTAINER_ID):/app
	docker start --attach --interactive $(CONTAINER_ID)

test:
	mvn --batch-mode test

clean:
	mvn clean

install:
	mvn install

run:
	mvn spring-boot:run