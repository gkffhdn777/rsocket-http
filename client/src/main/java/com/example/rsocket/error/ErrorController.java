package com.example.rsocket.error;

import com.example.config.RSocketClient;
import com.example.domain.ResponseMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

	private RSocketClient requester;

	public ErrorController(RSocketClient requester) {
		this.requester = requester;
	}

	@GetMapping("/error")
	public Flux<ResponseMessage> error() {
		return this.requester.monoRequester().flatMapMany(x -> {
			return x.route("error").data(Mono.empty())
					.retrieveFlux(ResponseMessage.class);
		});
	}
}
