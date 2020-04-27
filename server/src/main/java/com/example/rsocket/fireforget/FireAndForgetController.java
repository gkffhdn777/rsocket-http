package com.example.rsocket.fireforget;

import com.example.domain.RequestMessage;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class FireAndForgetController {

	@MessageMapping("fire-forget")
	public Mono<Void> fire(RequestMessage requestMessage) throws Exception {
		log.debug("Message : {}", requestMessage.getReqMessage());
		return Mono.empty();
	}
}
