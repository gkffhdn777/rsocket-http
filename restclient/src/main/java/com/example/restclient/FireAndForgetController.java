package com.example.restclient;

import com.example.RestClient;
import com.example.domain.RequestMessage;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;

@RestController
public class FireAndForgetController {

	@GetMapping("/fire-forget")
	public Mono<Void> fire() {
		return RestClient.webClient()
				.post()
				.uri("/fire-forget")
				.body(BodyInserters.fromValue(new RequestMessage("fire-forget")))
				.retrieve()
				.bodyToMono(Void.class);
	}
}
