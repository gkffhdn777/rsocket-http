package com.example.rest;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.stream.Stream;

import com.example.domain.RequestMessage;
import com.example.domain.ResponseMessage;
import reactor.core.publisher.Flux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StreamController {

	@PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ResponseMessage> greetStream(@RequestBody RequestMessage req) {
		return Flux.fromStream(Stream.generate(() -> new ResponseMessage(req.getReqMessage() +"/"+new Random().nextInt(400)+"/"+ Instant.now()))
		).delayElements(Duration.ofSeconds(1));
	}

}
