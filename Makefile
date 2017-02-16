IMAGE_ID := "myownradio/radioteria-base"
CONTAINER_ID := "radioteria_base"

build:
	docker build -t $(IMAGE_ID) .

push:
	docker push $(IMAGE_ID)
