
package org.example.config;

import org.apache.hc.client5.http.auth.AuthScope;
import org.apache.hc.client5.http.auth.UsernamePasswordCredentials;
import org.apache.hc.client5.http.impl.auth.BasicCredentialsProvider;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.routing.DefaultProxyRoutePlanner;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Configuration
public class FlutterWaveConfig {

    @Bean("flutterwaveRestClient")
    public RestClient flutterWaveRestClient(
            @Value("${fixie.url}") String fixieUrl) {

        URI uri = URI.create(fixieUrl);

        String[] auth = uri.getRawUserInfo().split(":", 2);

        HttpHost proxy = new HttpHost(
                "http",
                uri.getHost(),
                uri.getPort()
        );

        BasicCredentialsProvider credentials =
                new BasicCredentialsProvider();

        credentials.setCredentials(
                new AuthScope(proxy),
                new UsernamePasswordCredentials(
                        auth[0],
                        auth[1].toCharArray()
                )
        );

        CloseableHttpClient client = HttpClients.custom()
                .setRoutePlanner(
                        new DefaultProxyRoutePlanner(proxy))
                .setDefaultCredentialsProvider(credentials)
                .build();

        return RestClient.builder()
                .requestFactory(
                        new HttpComponentsClientHttpRequestFactory(client))
                .build();
    }
}
