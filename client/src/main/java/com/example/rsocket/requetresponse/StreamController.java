package com.example.rsocket.requetresponse;

import com.example.config.RSocketClient;
import com.example.domain.RequestMessage;
import com.example.domain.ResponseMessage;
import reactor.core.publisher.Flux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamController {

	private RSocketClient requester;

	public StreamController(RSocketClient requester) {
		this.requester = requester;
	}

	@GetMapping(value = "/stream/{data}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ResponseMessage> stream(@PathVariable String data) {
		return this.requester.monoRequester().flatMapMany(requester -> {
			return requester.route("stream")
					.data(new RequestMessage(data))
					.retrieveFlux(ResponseMessage.class);
		}).limitRequest(5);
	}
}
