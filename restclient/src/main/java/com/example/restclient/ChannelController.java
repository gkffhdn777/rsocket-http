package com.example.restclient;

import com.example.RestClient;
import com.example.domain.RequestMessage;
import com.example.domain.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ChannelController {

	@GetMapping(value = "/channel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ResponseMessage> channel() {
		return RestClient.webClient()
				.post()
				.uri("/channel")
				.body(Flux.range(0, 10)
						.map(i -> new RequestMessage("Client message restClient (" + i +")")), RequestMessage.class)
				.retrieve()
				.bodyToFlux(ResponseMessage.class)
				.onErrorResume(e -> Mono.error(new RuntimeException(e)));
	}
}
