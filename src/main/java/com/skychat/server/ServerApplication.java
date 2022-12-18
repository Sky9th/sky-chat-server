package com.skychat.server;

import com.skychat.server.enums.SocketType;
import com.skychat.server.socket.Server;
import com.skychat.server.socket.tcp.TcpSocketChannel;
import com.skychat.server.socket.web.WebSocketChannel;
import com.skychat.server.utils.MybatisUtils;
import io.netty.channel.ChannelInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ServerApplication {

	private static final Logger log = LoggerFactory.getLogger(ServerApplication.class);

	@Autowired
	Server socket;

	@Autowired
	TcpSocketChannel tcpSocketChannel;

	@Autowired
	WebSocketChannel webSocketChannel;

	@Autowired
	private MybatisUtils mybatisUtils;

	@Value("${spring.socket.channel}")
	private SocketType channel;


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
		ChannelInitializer channelInitializer;
		int port;
		switch (channel) {
			case TCPSOCKET:
				channelInitializer = tcpSocketChannel;
				port = 6666;
				break;
			case WEBSOCKET:
				channelInitializer = webSocketChannel;
				port = 6667;
				break;
			default:
				throw new InterruptedException("channel miss");
		}
		log.info("start "+ channel +" socket in port:" + port);
		socket.start(channelInitializer, port);
	}

}
