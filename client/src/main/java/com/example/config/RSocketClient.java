package com.example.config;

import java.net.InetSocketAddress;
import java.util.function.Supplier;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;
import reactor.util.retry.Retry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;

@Configuration
public class RSocketClient {

	@Autowired
	private RSocketStrategies rSocketStrategies;

	@Bean
	public Mono<RSocketRequester> monoRequester() {
		Supplier<InetSocketAddress> addressSupplier = () -> new InetSocketAddress("172.19.151.106", 7000);

		TcpClient tcpClient = TcpClient.create()
				.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.addressSupplier(addressSupplier)
				.doOnConnected(connection ->
						connection.addHandlerLast(new ReadTimeoutHandler(1000)));

		return RSocketRequester.builder()
				.rsocketFactory(r ->
						r.dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
								.frameDecoder(PayloadDecoder.ZERO_COPY)
				)
				.rsocketStrategies(rSocketStrategies)
				.connect(TcpClientTransport.create(tcpClient))
				.retryWhen(Retry.indefinitely())
				.cache();
	}

	/*public RSocket rSocket(){
		return RSocketFactory
				.connect()
				.dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
				.frameDecoder(PayloadDecoder.ZERO_COPY)
				.transport(TcpClientTransport.create(7000))
				.start()
				.block();
	}

	public RSocketRequester requester(RSocketStrategies rsocketStrategies){
		return RSocketRequester
				.wrap(this.rSocket(),
						MimeType.valueOf(MimeTypeUtils.APPLICATION_JSON_VALUE),null, rsocketStrategies);
	}*/
}
