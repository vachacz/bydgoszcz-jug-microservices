This project has been created as a demo project for a talk given on @BydgoszczJUG meetup. It's purpose is to demonstrate very simple microservices implementation with all tools needed to maintain the project in production environment.

Diagram below depitcs implemented use case:
![alt tag](https://github.com/vachacz/bydgoszcz-jug-microservices/blob/master/img/flow.png?raw=true)

Diagram below presents high level architecture of the demo:
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
