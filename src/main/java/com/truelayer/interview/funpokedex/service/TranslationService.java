package com.truelayer.interview.funpokedex.service;

import com.truelayer.interview.funpokedex.client.translator.TranslationClient;
import com.truelayer.interview.funpokedex.model.client.translator.TranslationApiResponse;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.model.enums.PokemonHabitat;
import com.truelayer.interview.funpokedex.model.enums.TranslatorType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationService {

    private final TranslationClient translationClient;

    public void translate(PokemonResponse pokemon) {
        TranslatorType translator = selectTranslator(pokemon);
        String translated = tryTranslate(translator, pokemon.getDescription());
        pokemon.setDescription(translated);
    }

    private TranslatorType selectTranslator(PokemonResponse pokemon) {
        if ((pokemon.getHabitat() != null && pokemon.getHabitat().equalsIgnoreCase(PokemonHabitat.CAVE.getHabitat()))
                || pokemon.isLegendary()) {
            return TranslatorType.YODA;
        }
        return TranslatorType.SHAKESPEARE;
    }

    private String tryTranslate(TranslatorType translator, String text) {
        try {
            TranslationApiResponse response = translationClient.getTranslation(translator.getAuthorName(), text);
            if (response != null && response.getContents() != null && response.getContents().getTranslated() != null) {
                return response.getContents().getTranslated();
            }
        } catch (Exception e) {
            log.warn("Translation API failed for {}: {}", translator.getAuthorName(), e.getMessage());
        }
        return text;
    }
}
