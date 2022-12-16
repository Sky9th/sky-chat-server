package com.skychat.server;

import com.skychat.server.dao.ChatDao;
import com.skychat.server.model.Chat;
import com.skychat.server.service.ChatService;
import com.skychat.server.service.UserService;
import com.skychat.server.socket.Server;
import com.skychat.server.utils.DataSourceConfigUtils;
import com.skychat.server.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
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

	@Autowired
	private MybatisUtils mybatisUtils;

	public static void main(String[] args) {
		log.info("start application");
		SpringApplication.run(ServerApplication.class, args);
	}

	/*@Bean
	public CommandLineRunner demo(UserService userService) {
		log.info("start runner");
		return (args) -> {
			SqlSession session = mybatisUtils.getSqlSession();
            ChatDao mapper = session.getMapper(ChatDao.class);
            Chat chat = new Chat(1,0, "test");
            log.info(chat.toString());
			Long id = mapper.insert(chat);
			session.commit();
			session.close();
			System.out.println(chat.getId());
			log.info("end runner");
		};
	}*/

	@Bean
	public void startSocket () throws InterruptedException {
		log.info("start socket");
		socket.start();
	}

}
