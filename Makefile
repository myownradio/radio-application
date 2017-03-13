IMAGE_ID := "peacefulbit/radioteria-service"
CONTAINER_ID := "radioteria_service"

docker-build:
	docker build -t $(IMAGE_ID) .

docker-push:
	docker push $(IMAGE_ID)

docker-run:
	docker run --name $(CONTAINER_ID) --rm -p 8080:8080 $(IMAGE_ID)

test:
	mvn --batch-mode test

clean:
	mvn clean

install:
	mvn install

fast-install:
	mvn -Dmaven.test.skip=true install

run:
	mvn spring-boot:run

webpack-watch:
	npm run watch --prefix src/main/frontend
