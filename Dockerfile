FROM maven:3.3-jdk-8

RUN apt-get update && apt-get install -y apt-utils ffmpeg && apt-get clean

