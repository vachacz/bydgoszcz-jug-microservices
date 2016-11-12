package pl.jug.bydgoszcz;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@EnableEurekaClient
@EnableFeignClients
@SpringBootApplication
@EnableHystrix
@EnableHystrixDashboard
@EnableBinding(Source.class)
public class BydgoszczJugApplication {

	public static void main(String[] args) {
		SpringApplication.run(BydgoszczJugApplication.class, args);
	}

	private static final Logger logger = LoggerFactory.getLogger(BydgoszczJugApplication.class);

	@org.springframework.web.bind.annotation.RestController
	public class RestController {

		@Autowired
		private SendingBean sendingBean;

		@Autowired
		private TorunJugClient torunJugClient;

		@HystrixCommand(fallbackMethod = "defaultFallback")
		@RequestMapping(path = "/inviteTorunJug", method = RequestMethod.GET)
		public String inviteTorunJug() {
			logger.info("InviteTorunJug entering");
			torunJugClient.invite();
			logger.info("InviteTorunJug executed");
			return "no worries, will do";
		}

		@HystrixCommand(fallbackMethod = "defaultFallback")
		@RequestMapping(path = "/ack", method = RequestMethod.GET)
		public String greet() {
			logger.info("Ack entering");
			torunJugClient.seeyou();

			logger.info("Ack sending gifts to Torun JUG");
			sendingBean.sendGifts("vouncher");

			logger.info("Ack executed");
			return "ack";
		}

		public String defaultFallback() {
			logger.info("Hystrix fallback");
			return "default";
		}

	}

	@FeignClient("torun-jug")
	public interface TorunJugClient {

		@RequestMapping(value = "/invite")
		void invite();

		@RequestMapping(value = "/seeyou")
		void seeyou();

	}

	@Component
	public class SendingBean {

		@Autowired
		private Source source;

		public void sendGifts(String body) {
			source.output().send(MessageBuilder.withPayload(body).build());
		}
	}

}
