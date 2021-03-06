package ir.snapp.phonebook.configuration.httpclient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import ir.snapp.phonebook.configuration.properties.WebClientProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

/**
 * This class manage how to initialize webClient and config it for more information about WebClient see
 * {@link WebClient} documentation
 *
 * @author Pouya Ashna
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebClientConfiguration {

    private final WebClientProperties webClientProperties;

    /**
     * Define how to initialize webClient
     *
     * @return WebClient instance
     */
    @Bean
    public WebClient webClient() {
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientProperties.getHttpClient().getConnectTimeout())
                .doOnConnected(connection ->
                        connection
                                .addHandlerLast(new ReadTimeoutHandler(webClientProperties.getHttpClient().getReadTimeout()))
                                .addHandlerLast(new WriteTimeoutHandler(webClientProperties.getHttpClient().getWriteTimeout()))
                );
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .filter(logRequest()).build();
    }

    /**
     * Config how to log request that occur with webclient
     *
     * @return configuration of log
     */
    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.debug("{}={}", name, value)));
            return next.exchange(clientRequest);
        };
    }

}
