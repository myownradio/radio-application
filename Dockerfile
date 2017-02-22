FROM peacefulbit/radioteria-base

COPY		. /app

WORKDIR		/app

RUN		mvn install

CMD		["make", "run"]
