package com.example.rsocket.requestresponse;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.example.domain.RequestMessage;
import com.example.domain.ResponseMessage;
import reactor.core.publisher.Mono;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ReqResModelController {

	final Map<String, String> unmodifiableMap;

	public ReqResModelController() {
		Map<String, String> map = new HashMap<>();
		map.put("1", "kimminsuk");
		map.put("2", "etc");
		unmodifiableMap = Collections.unmodifiableMap(map);
	}

	@MessageMapping("req-res-model")
	public Mono<ResponseMessage> fire(RequestMessage req) throws Exception {
		String resData = Optional
				.ofNullable(unmodifiableMap.get(req.getReqMessage()))
				.orElse("empty");
		//Integer random = new Random().nextInt(400) + 100;
		return Mono.just(new ResponseMessage(resData)).delayElement(Duration.ofMillis(500));
	}
}
