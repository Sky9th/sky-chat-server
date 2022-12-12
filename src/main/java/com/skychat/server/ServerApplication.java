package com.skychat.server;

import com.skychat.server.model.User;
import com.skychat.server.repository.UserRepository;
import com.skychat.server.service.UserService;
import com.skychat.server.socket.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class ServerApplication {

	private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

	@Autowired
	Server socket;

	public static void main(String[] args) {
		log.info("start application");
		SpringApplication.run(ServerApplication.class, args);
	}

	@Bean
	public void startSocket () throws InterruptedException {
		log.info("start socket");
		socket.start();
	}

	@Bean
	public CommandLineRunner demo(UserService userService) {
		return (args) -> {
			List<User> list =userService.findAll();
			log.info(list.get(0).toString());
		};
	}

}
