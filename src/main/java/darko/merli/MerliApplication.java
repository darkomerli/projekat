package darko.merli;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MerliApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MerliApplication.class, args);

		Automobil a = context.getBean(Automobil.class);

		a.show();
	}


}
