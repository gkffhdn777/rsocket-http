package com.example;

import java.time.Duration;
import io.rsocket.frame.decoder.PayloadDecoder;
import io.rsocket.transport.netty.client.TcpClientTransport;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.messaging.rsocket.RSocketStrategies;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

@Component
public class RSocketConnect {

	private RSocketStrategies rSocketStrategies;

	public RSocketConnect(RSocketStrategies rSocketStrategies) {
		this.rSocketStrategies = rSocketStrategies;
	}

	public Mono<RSocketRequester> otherRequester() {
		return RSocketRequester.builder()
				.rsocketFactory(r ->
						r.dataMimeType(MimeTypeUtils.APPLICATION_JSON_VALUE)
								.frameDecoder(PayloadDecoder.ZERO_COPY)
								.resume()
								.resumeSessionDuration(Duration.ofSeconds(1)))
				.rsocketStrategies(rSocketStrategies)
				.connect(TcpClientTransport.create(8000))
				.retryWhen(Retry.backoff(110, Duration.ofSeconds(5)))
				.cache();
	}
}
