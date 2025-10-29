package com.truelayer.interview.funpokedex.client.pokemon;

import com.truelayer.interview.funpokedex.model.client.pokemon.PokemonApiResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange(accept = "application/json")
public interface PokemonClient {

    @GetExchange("/pokemon-species/{name}")
    PokemonApiResponse getPokemonByName(@PathVariable("name") String name);
}