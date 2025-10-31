package com.truelayer.interview.funpokedex.client.translator;

import com.truelayer.interview.funpokedex.client.pokemon.PokemonClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class TranslationClientConfig {

    @Value("${client.api.base.url.translator}")
    private String baseUrl;

    @Bean
    public TranslationClient translationClient () {
        RestClient restClient = RestClient.builder().baseUrl(baseUrl).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder().exchangeAdapter(adapter).build();
        return factory.createClient(TranslationClient.class);
    }
}