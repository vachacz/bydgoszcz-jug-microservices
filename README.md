This project has been created as a demo project for a talk given on [@BydgoszczJUG](https://twitter.com/BydgoszczJUG) meetup. It's purpose is to demonstrate very simple microservices implementation with all tools needed to maintain the project in production environment.

Diagram below depitcs implemented use case:<br />
![alt tag](https://github.com/vachacz/bydgoszcz-jug-microservices/blob/master/img/flow.png?raw=true)

Diagram below presents high level architecture of the demo:<br />
![alt tag](https://github.com/vachacz/bydgoszcz-jug-microservices/blob/master/img/arch.png?raw=true)

All components used in the demo project are started using docker.

# Build

In order to build all docker images execute:
> ./gradlew buildDocker

To start all supporting tools execute:
> docker-compose -f docker-compose-tools.yml up

To start microservices execute:
> docker-compose -f docker-compose-microservices.yml up

Please not that docker compose files has been splitted, to make developement of the microservices easier. If you want to change anything in the microservcies, you need to rebuild and restart docker images of both microservices, without touching all supporting tools.

# Testing

In order to see generate some traffic and see that all tools are running properly, you can execute an URL in the browser:

> http://localhost:8081/inviteTorunJug

or you can generate some traffic using ab tool as follows:

> ab -c 5 -n 100000 http://localhost:8081/inviteTorunJug

# Links

+ Eureka Dashboard
 + <http://localhost:8761/>

+ Zipkin
 + <http://localhost:9411/>

+ RabbitMQ management console
 + <http://localhost:15672/>

+ Hystrix and Turbine dashboards
 + [Torun-JUG](http://localhost:8082/hystrix/monitor?stream=http%3A%2F%2Fservice-torun-jug%3A8080%2Fhystrix.stream)
 + [Bydgoszcz-JUG](http://localhost:8081/hystrix/monitor?stream=http%3A%2F%2Fservice-bydgoszcz-jug%3A8080%2Fhystrix.stream)
 + [Turbine](http://localhost:9090/hystrix/monitor?stream=http%3A%2F%2Fservice-turbine%3A8080%2Fturbine.stream)

+ Kibana
 + <http://localhost:5601/app/kibana#/management/kibana/index/?_g=()>

+ Grafana
 + <http://localhost:3000/>

+ Graphite UI
 + <http://localhost:7070/>
 
 Please note that, when you start the example on Windows, you will need to replace the localhost with IP address of the docker host.
