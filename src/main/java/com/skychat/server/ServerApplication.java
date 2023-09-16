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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ServerApplication {


	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}


}
