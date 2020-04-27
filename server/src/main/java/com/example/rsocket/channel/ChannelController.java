package com.example.rsocket.channel;

import java.time.Duration;
import java.time.Instant;

import com.example.RSocketConnect;
import com.example.domain.RequestMessage;
import com.example.domain.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class ChannelController {

	private RSocketConnect connect;

	public ChannelController(RSocketConnect connect) {
		this.connect = connect;
	}

	@MessageMapping("channel")
	public Flux<ResponseMessage> channel(@Payload Flux<RequestMessage> p) {
		log.debug("received channel start : {} ", Instant.now());
		return p.delayElements(Duration.ofSeconds(1))
				.map(m -> new ResponseMessage("channel : (" + m + ") at " + Instant.now()))
				.onErrorResume(e -> Mono.error(new RuntimeException(e)));
	}

	@MessageMapping("channel.server")
	public Flux<ResponseMessage> channelServer(@Payload Flux<RequestMessage> p){
		log.debug("received channel.server start : {} ", Instant.now());
		return p.delayElements(Duration.ofSeconds(1))
				.flatMap(m -> {
					return this.connect.otherRequester()
							.flatMapMany(x -> {
						return x.route("other")
								.data(Flux.range(0, 2)
										.map(i -> new RequestMessage(m.getReqMessage() + " server (" + i +")"))
								)
								.retrieveFlux(ResponseMessage.class);
					}).flatMap(res -> {
						return Mono.just(new ResponseMessage("channel : (" + res + ") at " + Instant.now()));
					});
				});
	}
}
