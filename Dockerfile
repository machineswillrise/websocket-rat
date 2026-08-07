FROM bellsoft/liberica-openjdk-debian:21
ARG USER=rat
ENV DEBIAN_FRONTEND=noninteractive

# create user
RUN useradd ${USER} --create-home

# set up syslog
RUN apt update
RUN apt upgrade -y
RUN apt install rsyslog -y
RUN /usr/sbin/rsyslogd

# run jar
COPY rat-server/target/rat-server-*-jar-with-dependencies.jar /home/${USER}/server.jar
USER ${USER}
RUN java -jar ~/server.jar
