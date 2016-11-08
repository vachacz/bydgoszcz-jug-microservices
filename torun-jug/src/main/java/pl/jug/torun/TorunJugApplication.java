package pl.jug.torun;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

		@RequestMapping(path = "/greet", method = RequestMethod.GET)
		public String getGreet() {
			logger.info("Greet executed");
			return "greet";
		}

		@RequestMapping(path = "/super", method = RequestMethod.GET)
		public String getSuper() {
			logger.info("Super executed");
			return "super";
		}

	}

}
