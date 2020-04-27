package com.example.rest;

import com.example.domain.RequestMessage;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class FireAndForgetController {

	@PostMapping("/fire-forget")
	public Mono<Void> fire(@RequestBody RequestMessage requestMessage) throws Exception {
		log.debug("Message : {}", requestMessage.getReqMessage());
		return Mono.empty();
	}
}
