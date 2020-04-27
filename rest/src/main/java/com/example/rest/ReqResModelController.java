package com.example.rest;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.domain.ResponseMessage;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReqResModelController {

	final Map<String, String> unmodifiableMap;

	public ReqResModelController() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "kimminsuk");
		map.put("3", "etc");
		this.unmodifiableMap = Collections.unmodifiableMap(map);
	}

	@GetMapping("/req-res-model/{id}")
	public Mono<ResponseMessage> reqResModel(@PathVariable String id) throws Exception {
		String resData = Optional
				.ofNullable(unmodifiableMap.get(id))
				.orElse("empty");
		return Mono.just(new ResponseMessage(resData)).delayElement(Duration.ofMillis(500));
	}

	@GetMapping("/test")
	public Mono<String> test(){
		return Mono.just("OK!");
	}
}
