package com.truelayer.interview.funpokedex.service;

import com.truelayer.interview.funpokedex.client.pokemon.PokemonClient;
import com.truelayer.interview.funpokedex.mapper.PokemonMapper;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.model.client.pokemon.PokemonApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonService {

    private final PokemonClient pokemonClient;
    private final TranslationService translationService;
    private final PokemonMapper pokemonMapper;

    public PokemonResponse getPokemonInfo(String name) {
        PokemonApiResponse apiResponse = pokemonClient.getPokemonByName(name);
        return pokemonMapper.toDomain(apiResponse);
    }

    public PokemonResponse getTranslatedPokemonInfo(String name) {
        PokemonResponse pokemon = getPokemonInfo(name);
        translationService.translate(pokemon);
        return pokemon;
    }
}
