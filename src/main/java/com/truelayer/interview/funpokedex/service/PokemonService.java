package com.truelayer.interview.funpokedex.service;

import com.truelayer.interview.funpokedex.client.pokemon.PokemonClient;
import com.truelayer.interview.funpokedex.model.PokemonResponse;
import com.truelayer.interview.funpokedex.model.client.pokemon.FlavorTextEntry;
import com.truelayer.interview.funpokedex.model.client.pokemon.PokemonApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonService {
    private final PokemonClient pokemonClient;
    private final TranslationService translationService;


    public PokemonResponse getPokemonInfo(String name) {
        PokemonApiResponse apiResponse = pokemonClient.getPokemonByName(name);
        return PokemonResponse.builder()
                .name(apiResponse.getName())
                .description(
                    apiResponse.getFlavorTextEntries().stream()
                        .filter(t -> t.getLanguage() != null && "en".equals(t.getLanguage().getName()))
                        .findFirst()
                        .map(FlavorTextEntry::getFlavorText)
                        .orElse("")
                )
                .habitat(apiResponse.getHabitat() != null ? apiResponse.getHabitat().getName() : "")                .habitat(apiResponse.getHabitat() != null ? apiResponse.getHabitat().getName() : "")
                .isLegendary(apiResponse.isLegendary())
                .build();
    }

    public PokemonResponse getTranslatedPokemonInfo(String name) {
        PokemonResponse pokemon = this.getPokemonInfo(name);

        return translationService.translate(pokemon);
    }
}
