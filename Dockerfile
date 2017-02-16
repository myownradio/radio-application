FROM maven:3.3-jdk-8

RUN apt-get update && apt-get install -y ffmpeg make && apt-get clean

