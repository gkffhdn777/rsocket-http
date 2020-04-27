package com.example.rsocket.requetstream;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.stream.Stream;

import com.example.domain.RequestMessage;
import com.example.domain.ResponseMessage;
import reactor.core.publisher.Flux;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class StreamController {

	@MessageMapping("stream")
	Flux<ResponseMessage> greetStream(RequestMessage req) {
		return Flux.fromStream(Stream.generate(() -> new ResponseMessage(req.getReqMessage() +"/"+new Random().nextInt(400)+"/"+ Instant.now()))
		).delayElements(Duration.ofSeconds(1));
	}
}
