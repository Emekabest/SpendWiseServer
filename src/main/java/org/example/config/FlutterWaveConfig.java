package org.example.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.net.URI;

@Configuration
public class FlutterWaveConfig {

    @Bean("flutterwaveRestClient")
    public RestClient flutterWaveRestClient(@Value("${http://fixie:rD96oIPz0RSXWxJ@ventoux.usefixie.com:80}") String fixieUrl){

        URI uri = URI.create(fixieUrl);

        String[] auth = uri.getRawUserInfo().split(":", 2);

        HttpHost proxy = new HttpHost(uri.getHost(), uri.getPort());

        BasicCredentialsProvider credentials =
                new BasicCredentialsProvider();

        credentials.setCredentials(
                new AuthScope(proxy),
                new UsernamePasswordCredentials(
                        auth[0], auth[1]
                )
        );

        CloseableHttpClient client = HttpClients.custom()
                .setRoutePlanner(new DefaultProxyRoutePlanner(proxy))
                .setDefaultCredentialsProvider(credentials)
                .build();

        return RestClient.builder()
                .requestFactory(
                        new HttpComponentsClientHttpRequestFactory((HttpClient) client))
                .build();



    }


}
