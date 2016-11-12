package pl.jug.torun;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
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
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
public class TorunJugApplication {

	public static void main(String[] args) {
		SpringApplication.run(TorunJugApplication.class, args);
	}

	private static final Logger logger = LoggerFactory.getLogger(TorunJugApplication.class);

	@org.springframework.web.bind.annotation.RestController
	public class RestController {

		@Autowired
		private BydgoszczJugClient bydgoszczJugClient;

		@HystrixCommand(fallbackMethod = "defaultFallback")
		@RequestMapping(path = "/invite", method = RequestMethod.GET)
		public String invite() {
			logger.info("Invite executed");
			bydgoszczJugClient.ack();
			logger.info("Seeyou executed");
			return "invited";
		}

		@HystrixCommand(fallbackMethod = "defaultFallback")
		@RequestMapping(path = "/seeyou", method = RequestMethod.GET)
		public String seeyou() {
			logger.info("Seeyou entering");
			logger.info("Seeyou executed");
			return "seeyou";
		}

		public String defaultFallback() {
			logger.info("Hystrix fallback");
			return "default";
		}

	}

	@EnableBinding(Sink.class)
	public class TimerSource {

		@StreamListener(Sink.INPUT)
		public void processVote(String gift) {
			logger.info("Gift recieved " + gift);
		}

	}

	@FeignClient("bydgoszcz-jug")
	public interface BydgoszczJugClient {

		@RequestMapping(value = "/ack")
		void ack();

	}

}
