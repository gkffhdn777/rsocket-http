package com.example.rsocket.requeststream;

import com.example.config.RSocketClient;
import com.example.domain.RequestMessage;
import com.example.domain.ResponseMessage;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReqResModelController {

	private RSocketClient requester;

	public ReqResModelController(RSocketClient requester) {
		this.requester = requester;
	}

	@GetMapping("/req-res-model/{id}")
	Mono<ResponseMessage> reqResModel(@PathVariable String id) {
		return this.requester.monoRequester().flatMap(requester -> {
			return requester.route("req-res-model")
					.data(new RequestMessage(id))
					.retrieveMono(ResponseMessage.class);
		});
	}
}
