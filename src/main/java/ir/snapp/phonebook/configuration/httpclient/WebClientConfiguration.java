package ir.snapp.phonebook.configuration.httpclient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

@Slf4j
@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient() {
        TcpClient tcpClient = TcpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000)
                .doOnConnected(connection ->
                        connection.addHandlerLast(new ReadTimeoutHandler(10))
                                .addHandlerLast(new WriteTimeoutHandler(10)));
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .filter(logRequest()).build();
    }

    private ExchangeFilterFunction logRequest() {
        return (clientRequest, next) -> {
            log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
            clientRequest.headers()
                    .forEach((name, values) -> values.forEach(value -> log.debug("{}={}", name, value)));
            return next.exchange(clientRequest);
        };
    }

}
