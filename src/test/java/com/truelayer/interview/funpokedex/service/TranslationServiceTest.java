package com.truelayer.interview.funpokedex.service;

import com.truelayer.interview.funpokedex.client.translator.TranslationClient;
import com.truelayer.interview.funpokedex.model.client.translator.TranslationApiResponse;
import com.truelayer.interview.funpokedex.model.client.translator.TranslationContent;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.model.enums.PokemonHabitat;
import com.truelayer.interview.funpokedex.model.enums.TranslatorType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TranslationService
 */
@ExtendWith(MockitoExtension.class)
class TranslationServiceTest {

    @Mock
    private TranslationClient translationClient;

    @InjectMocks
    private TranslationService translationService;

    private PokemonResponse pokemon;

    @BeforeEach
    void setUp() {
        pokemon = new PokemonResponse();
        pokemon.setName("mewtwo");
        pokemon.setDescription("A powerful psychic pokemon.");
    }

    @Test
    void shouldUseYodaTranslator_WhenHabitatIsCave() {
        // given
        pokemon.setHabitat(PokemonHabitat.CAVE.getHabitat());
        pokemon.setLegendary(false);

        TranslationApiResponse apiResponse = successResponse("Yoda speaks strangely");
        when(translationClient.getTranslation(eq(TranslatorType.YODA.getAuthorName()), anyString()))
                .thenReturn(apiResponse);

        // when
        translationService.translate(pokemon);

        // then
        assertThat(pokemon.getDescription()).isEqualTo("Yoda speaks strangely");
        verify(translationClient).getTranslation(eq("yoda"), anyString());
    }

    @Test
    void shouldUseYodaTranslator_WhenPokemonIsLegendary() {
        // given
        pokemon.setLegendary(true);
        TranslationApiResponse apiResponse = successResponse("Legendary translation");
        when(translationClient.getTranslation(eq("yoda"), anyString()))
                .thenReturn(apiResponse);

        // when
        translationService.translate(pokemon);

        // then
        assertThat(pokemon.getDescription()).isEqualTo("Legendary translation");
        verify(translationClient).getTranslation(eq("yoda"), anyString());
    }

    @Test
    void shouldUseShakespeareTranslator_WhenNonLegendaryNonCave() {
        // given
        pokemon.setHabitat(PokemonHabitat.FOREST.getHabitat());
        TranslationApiResponse apiResponse = successResponse("Thou art a forest dweller");
        when(translationClient.getTranslation(eq("shakespeare"), anyString()))
                .thenReturn(apiResponse);

        // when
        translationService.translate(pokemon);

        // then
        assertThat(pokemon.getDescription()).isEqualTo("Thou art a forest dweller");
        verify(translationClient).getTranslation(eq("shakespeare"), anyString());
    }

    @Test
    void shouldFallbackToOriginal_WhenTranslationApiFails() {
        // given
        pokemon.setLegendary(true);
        when(translationClient.getTranslation(anyString(), anyString()))
                .thenThrow(new RuntimeException("API error"));

        // when
        translationService.translate(pokemon);

        // then
        assertThat(pokemon.getDescription()).isEqualTo("A powerful psychic pokemon.");
        verify(translationClient).getTranslation(eq("yoda"), anyString());
    }

    @Test
    void shouldFallbackToOriginal_WhenResponseIsNull() {
        // given
        pokemon.setLegendary(true);
        when(translationClient.getTranslation(anyString(), anyString()))
                .thenReturn(null);

        // when
        translationService.translate(pokemon);

        // then
        assertThat(pokemon.getDescription()).isEqualTo("A powerful psychic pokemon.");
    }

    @Test
    void shouldFallbackToOriginal_WhenTranslatedFieldIsMissing() {
        // given
        pokemon.setLegendary(true);
        TranslationApiResponse responseWithoutContent = new TranslationApiResponse();
        when(translationClient.getTranslation(anyString(), anyString()))
                .thenReturn(responseWithoutContent);

        // when
        translationService.translate(pokemon);

        // then
        assertThat(pokemon.getDescription()).isEqualTo("A powerful psychic pokemon.");
    }

    // --- helper ---
    private TranslationApiResponse successResponse(String translatedText) {
        return TranslationApiResponse.builder()
                .contents(
                        TranslationContent.builder()
                                .translated(translatedText)
                                .build()
                )
                .build();
    }
}
