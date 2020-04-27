package com.example.restclient;

import com.example.RestClient;
import com.example.domain.RequestMessage;
import com.example.domain.ResponseMessage;
import reactor.core.publisher.Flux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;

@RestController
public class StreamController {

	@GetMapping(value = "/stream/{data}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ResponseMessage> stream(@PathVariable String data) {
		return RestClient.webClient()
				.post()
				.uri("/stream")
				.body(BodyInserters.fromValue(new RequestMessage(data)))
				.retrieve()
				.bodyToFlux(ResponseMessage.class)
				.limitRequest(5);
	}

}
