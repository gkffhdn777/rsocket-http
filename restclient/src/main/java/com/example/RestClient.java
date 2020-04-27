package com.example;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

public class RestClient {

	public static WebClient webClient() {
		TcpClient tcpClient = TcpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
				.doOnConnected(connection ->
						connection.addHandlerLast(new ReadTimeoutHandler(1000)));
		return WebClient
				.builder()
				.baseUrl("http://172.19.148.184:7001")
				//.baseUrl("http://localhost:7001")
				.clientConnector(new ReactorClientHttpConnector(
						HttpClient.from(tcpClient.option(ChannelOption.SO_KEEPALIVE, true)).keepAlive(true))
				).build();
	}
}
