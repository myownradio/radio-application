FROM peacefulbit/radioteria-base

COPY		. /app

WORKDIR		/app

RUN		mvn install

CMD		["mvn", "spring-boot:run"]
