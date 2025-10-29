package com.truelayer.interview.funpokedex.service;


import com.truelayer.interview.funpokedex.client.translator.TranslationClient;
import com.truelayer.interview.funpokedex.model.PokemonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TranslationService {
    private final TranslationClient translationClient;

    public PokemonResponse translate(PokemonResponse pokemon) {
        String authorName = (pokemon.getHabitat() != null && pokemon.getHabitat().equalsIgnoreCase("cave")) || pokemon.isLegendary()
                ? "yoda"
                : "shakespeare";

        String translatedText = translationClient.getTranslation(authorName, pokemon.getDescription())
                .getContent()
                .getTranslated();

        return PokemonResponse.builder()
                .name(pokemon.getName())
                .description(translatedText)
                .habitat(pokemon.getHabitat())
                .isLegendary(pokemon.isLegendary())
                .build();
    }
}
