package darko.merli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MerliApplication {

	public static void main(String[] args) {
		SpringApplication.run(MerliApplication.class, args);
	}


}
