package admin_service.demo;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;


@Configuration
@EnableAdminServer
@SpringBootApplication
public class AdminServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(AdminServiceApplication.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        String serverPort = environment.getProperty("server.port");
		System.out.println(".................................");
		System.out.println("..........start success..........");
		System.out.println("SpringBootAdmin: http://localhost:" + serverPort);
		System.out.println(".................................");
    }
}
