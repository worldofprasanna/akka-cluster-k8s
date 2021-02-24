FROM wgmouton/sbt-ubuntu

RUN mkdir -p /opt/app

WORKDIR /opt/app

ADD build.sbt build.sbt
ADD project project

RUN sbt package

RUN apt install -y fakeroot

ADD . .

RUN sbt compile

CMD ["sbt", "run"]
