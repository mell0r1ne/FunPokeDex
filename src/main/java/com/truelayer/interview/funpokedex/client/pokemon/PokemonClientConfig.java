package com.truelayer.interview.funpokedex.client.pokemon;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class PokemonClientConfig {
    private final static String baseUrl = "https://pokeapi.co/api/v2";

    @Bean
    public RestClient pokeApiRestClient() {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public PokemonClient pokemonClient(RestClient pokeApiRestClient) {
        RestClientAdapter adapter = RestClientAdapter.create(pokeApiRestClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builder().exchangeAdapter(adapter).build();
        return factory.createClient(PokemonClient.class);
    }
}