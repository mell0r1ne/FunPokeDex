package com.truelayer.interview.funpokedex.service;

import com.truelayer.interview.funpokedex.client.translator.TranslationClient;
import com.truelayer.interview.funpokedex.model.client.translator.TranslationApiResponse;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.model.enums.PokemonHabitat;
import com.truelayer.interview.funpokedex.model.enums.TranslatorType;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TranslationService {

    private final TranslationClient translationClient;
    private final UtilService utilService;

    /**
     * Translates the Pokémon description using the appropriate translator.
     * @param pokemon the Pokémon response object to translate
     */
    public void translate(PokemonResponse pokemon) {
        if (pokemon == null || pokemon.getDescription() == null || pokemon.getDescription().isBlank()) {
            log.debug("No translation needed: pokemon or description is null/blank");
            return;
        }
        TranslatorType translator = selectTranslator(pokemon);
        Optional<String> translated = tryTranslate(translator, pokemon.getDescription());
        translated.ifPresent(pokemon::setDescription);
    }

    /**
     * Selects the appropriate translator based on Pokémon habitat and legendary status.
     * @param pokemon the Pokémon response object
     * @return the selected TranslatorType
     */
    private TranslatorType selectTranslator(PokemonResponse pokemon) {
        if ((pokemon.getHabitat() != null && pokemon.getHabitat().equalsIgnoreCase(PokemonHabitat.CAVE.getHabitat()))
                || pokemon.isLegendary()) {
            return TranslatorType.YODA;
        }
        return TranslatorType.SHAKESPEARE;
    }

    /**
     * Attempts to translate the given text using the specified translator.
     * Returns Optional.empty() if translation fails, or Optional.of(original text) if input is blank/null.
     * @param translator the translator type
     * @param text the text to translate
     * @return Optional containing the translated text, or empty if translation failed
     */
    private Optional<String> tryTranslate(TranslatorType translator, String text) {
        if (text == null || text.isBlank()) {
            log.debug("No translation attempted: input text is null or blank");
            return Optional.ofNullable(text);
        }
        try {
            TranslationApiResponse response = translationClient.getTranslation(translator.getAuthorName(), text);
            if (response != null && response.getContents() != null && response.getContents().getTranslated() != null) {
                log.debug("Successfully translated text with {} translator", translator.getAuthorName());
                return Optional.of(response.getContents().getTranslated());
            } else {
                log.warn("Translation API returned null or incomplete response for translator {}. Returning original text. Text: {}", translator.getAuthorName(), utilService.sanitizeForLogging(text));
            }
        } catch (Exception e) {
            log.warn("Translation API failed for {}. Returning original text. Text: {}. Error: {}", translator.getAuthorName(), utilService.sanitizeForLogging(text), e.getMessage());
        }
        return Optional.of(text);
    }

}
