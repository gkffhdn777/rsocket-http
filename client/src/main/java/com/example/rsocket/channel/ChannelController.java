package com.example.rsocket.channel;

import java.time.Duration;

import com.example.config.RSocketClient;
import com.example.domain.RequestMessage;
import com.example.domain.ResponseMessage;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import org.springframework.http.MediaType;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ChannelController {

	private RSocketClient requester;

	private Mono<RSocketRequester> monoOtherRequester;

	private RSocketStrategies rSocketStrategies;

	public ChannelController(RSocketClient requester, RSocketStrategies rSocketStrategies) {
		this.requester = requester;
		this.rSocketStrategies = rSocketStrategies;
		this.monoOtherRequester = RSocketRequester.builder()
				.rsocketFactory(r ->
						r.dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
								.frameDecoder(PayloadDecoder.ZERO_COPY)
				)
				.rsocketStrategies(this.rSocketStrategies)
				.connect(TcpClientTransport.create(8000))
				.retryWhen(Retry.backoff(110, Duration.ofSeconds(5)))
				.cache();
	}

	@GetMapping(value = "channel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ResponseMessage> channel() {
		return requester.monoRequester().flatMapMany(requester -> {
			return requester.route("channel")
					.data(
							Flux.range(0, 10)
									.map(i -> new RequestMessage("Client message RSocket (" + i +")"))
					)
					.retrieveFlux(ResponseMessage.class)
					.doOnNext(msg -> log.info("received messages::" + msg));
		});
	}

	@GetMapping(value = "/channel/server", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ResponseMessage> channelServer() {
		return requester.monoRequester().flatMapMany(requester -> {
			return requester.route("channel.server")
					.data(
							Flux.range(0, 10)
									.map(i -> new RequestMessage("Client message RSocket (" + i +") =>"))
					)
					.retrieveFlux(ResponseMessage.class)
					.doOnNext(msg -> log.info("received messages::" + msg));
		});

	}

	@GetMapping(value = "/channel/other/server", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ResponseMessage> channelOtherServer() {
		return this.monoOtherRequester.flatMapMany(requester -> {
			return requester.route("other")
					.data(
							Flux.range(0, 1)
									.map(i -> new RequestMessage("opposite data.."))
					)
					.retrieveFlux(ResponseMessage.class)
					.doOnNext(msg -> log.info("received messages::" + msg));
		});
	}
}