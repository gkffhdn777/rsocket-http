package com.example.rsocket.controller;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import com.example.domain.RequestMessage;
import com.example.domain.ResponseMessage;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

@Controller
public class OtherServerController {

	private static final Map<String, String> store = new HashMap<>();

	@MessageMapping("other")
	public Flux<ResponseMessage> other(@Payload Flux<RequestMessage> p) {

		return p.delayElements(Duration.ofSeconds(1)).flatMap(x -> {
			if ("opposite".equals(x.getReqMessage())) {
				store.put("key", x.getReqMessage());
			}
			return Mono.just(new ResponseMessage(x.getReqMessage() + " => other server store : " + store.get("key")));
		}).log("-> reactive log ->");
	}

}
