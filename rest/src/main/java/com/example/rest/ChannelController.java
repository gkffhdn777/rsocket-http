package com.example.rest;

import java.time.Duration;
import java.time.Instant;

import com.example.domain.RequestMessage;
import com.example.domain.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ChannelController {

	@PostMapping(value = "/channel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ResponseMessage> channel(@RequestBody Flux<RequestMessage> p) {
		log.debug("received rest channel start : {} ", Instant.now());
		return p.delayElements(Duration.ofSeconds(1))
				.map(m -> new ResponseMessage("channel : (" + m + ") at " + Instant.now()));
	}
}
