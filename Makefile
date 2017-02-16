IMAGE_ID := "myownradio/radioteria-base"
CONTAINER_ID := "radioteria_base"

docker-build:
	docker build -t $(IMAGE_ID) .

docker-push:
	docker push $(IMAGE_ID)

docker-test:
	docker create --name $(CONTAINER_ID) --rm -w /app $(IMAGE_ID) mvn test
	docker cp . $(CONTAINER_ID):/app
	docker start --attach $(CONTAINER_ID)

test:
	mvn --batch-mode test

clean:
	mvn clean

install:
	mvn install
