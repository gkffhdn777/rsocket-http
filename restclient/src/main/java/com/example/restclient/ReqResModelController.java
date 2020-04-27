package com.example.restclient;

import com.example.RestClient;
import com.example.domain.ResponseMessage;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RestController
public class ReqResModelController {

	@GetMapping("/req-res-model/{id}")
	public Mono<ResponseMessage> reqResModel(@PathVariable String id) {
		return RestClient.webClient().get()
				.uri("/req-res-model/{id}", id)
				.retrieve()
				.bodyToMono(ResponseMessage.class)
				.onErrorResume(ex ->{
					log.error(ex.getMessage(), ex);
					return Mono.error(new RuntimeException(ex));
				});
	}

	@GetMapping("/test")
	public Mono<String> test() {
		return Mono.just("ok!");
	}
}
