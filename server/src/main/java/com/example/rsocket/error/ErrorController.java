package com.example.rsocket.error;

import com.example.domain.ResponseMessage;
import reactor.core.publisher.Flux;

import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ErrorController {

	@MessageMapping("error")
	Flux<ResponseMessage> error() {
		return Flux.error(new IllegalArgumentException());
	}

	@MessageExceptionHandler
	Flux<ResponseMessage> errorHandler(IllegalArgumentException iae) {
		return Flux.just(new ResponseMessage("Error message : " + iae));
	}
}
