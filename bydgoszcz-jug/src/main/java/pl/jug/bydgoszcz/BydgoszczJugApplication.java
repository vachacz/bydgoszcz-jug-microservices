package pl.jug.bydgoszcz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
public class BydgoszczJugApplication {

	public static void main(String[] args) {
		SpringApplication.run(BydgoszczJugApplication.class, args);
	}

	private static final Logger logger = LoggerFactory.getLogger(BydgoszczJugApplication.class);

	@org.springframework.web.bind.annotation.RestController
	public class RestController {

		@Autowired
		private TorunJugClient torunJugClient;

		@RequestMapping(path = "/inviteTorunJug", method = RequestMethod.GET)
		public String inviteTorunJug() {
			logger.info("InviteTorunJug executed");
			torunJugClient.invite();
			return "ack";
		}

		@RequestMapping(path = "/ack", method = RequestMethod.GET)
		public String greet() {
			logger.info("Ack executed");
			torunJugClient.seeyou();
			return "ack";
		}

	}

	@FeignClient("torun-jug")
	public interface TorunJugClient {

		@RequestMapping(value = "/invite")
		void invite();

		@RequestMapping(value = "/seeyou")
		void seeyou();

	}

}
