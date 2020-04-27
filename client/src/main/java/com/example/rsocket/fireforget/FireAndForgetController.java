package com.example.rsocket.fireforget;

import com.example.config.RSocketClient;
import com.example.domain.RequestMessage;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FireAndForgetController {

	private RSocketClient requester;

	public FireAndForgetController(RSocketClient requester) {
		this.requester = requester;
	}

	@GetMapping("/fire-forget")
	public Mono<Void> fire(){
		return requester.monoRequester().flatMap(requester -> {
			return requester.route("fire-forget")
					.data(new RequestMessage("fire-forget"))
					.send();
		});
	}
}
