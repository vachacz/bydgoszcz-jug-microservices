FROM logstash

RUN apt-get update && apt-get install -y openssh-server apache2 supervisor
RUN mkdir -p /var/lock/apache2 /var/run/apache2 /var/run/sshd /var/log/supervisor

COPY src/main/docker/supervisord.conf /etc/supervisor/conf.d/supervisord.conf
COPY src/main/docker/logstash.conf /logstash.conf

ADD build/libs/bydgoszcz-jug-0.0.1-SNAPSHOT.jar app.jar
RUN sh -c "touch /app.jar"

EXPOSE 8080
ENTRYPOINT ["/usr/bin/supervisord"]