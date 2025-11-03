package com.truelayer.interview.funpokedex.service;

import com.truelayer.interview.funpokedex.client.pokemon.PokemonClient;
import com.truelayer.interview.funpokedex.mapper.PokemonMapper;
import com.truelayer.interview.funpokedex.model.client.pokemon.PokemonApiResponse;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PokemonService.
 */
@ExtendWith(MockitoExtension.class)
class PokemonServiceTest {

    @Mock
    private PokemonClient pokemonClient;

    @Mock
    private TranslationService translationService;

    @Mock
    private PokemonMapper pokemonMapper;

    @InjectMocks
    private PokemonService pokemonService;

    private PokemonApiResponse apiResponse;
    private PokemonResponse mappedResponse;

    @BeforeEach
    void setup() {
        apiResponse = new PokemonApiResponse();
        mappedResponse = PokemonResponse.builder()
                .name("bulbasaur")
                .description("A strange seed was planted on its back at birth.")
                .habitat("grassland")
                .isLegendary(false)
                .build();
    }

    // --- TESTS ---

    @Test
    void shouldFetchAndMapPokemonInfo() {
        // given
        when(pokemonClient.getPokemonByName("bulbasaur")).thenReturn(apiResponse);
        when(pokemonMapper.toDomain(apiResponse)).thenReturn(mappedResponse);

        // when
        PokemonResponse result = pokemonService.getPokemonInfo("bulbasaur");

        // then
        assertThat(result).isEqualTo(mappedResponse);
        verify(pokemonClient).getPokemonByName("bulbasaur");
        verify(pokemonMapper).toDomain(apiResponse);
        verifyNoInteractions(translationService);
    }

    @Test
    void shouldFetchMapAndTranslatePokemonInfo() {
        // given
        when(pokemonClient.getPokemonByName("mewtwo")).thenReturn(apiResponse);
        when(pokemonMapper.toDomain(apiResponse)).thenReturn(mappedResponse);

        // when
        PokemonResponse result = pokemonService.getTranslatedPokemonInfo("mewtwo");

        // then
        assertThat(result).isEqualTo(mappedResponse);
        verify(pokemonClient).getPokemonByName("mewtwo");
        verify(pokemonMapper).toDomain(apiResponse);
        verify(translationService).translate(mappedResponse);
    }

    @Test
    void shouldThrowPokemonNotFoundException_WhenApiResponseIsNull() {
        // given
        when(pokemonClient.getPokemonByName("missingno")).thenReturn(null);
        // when & then
        try {
            pokemonService.getPokemonInfo("missingno");
            fail("Expected PokemonNotFoundException");
        } catch (com.truelayer.interview.funpokedex.exception.PokemonNotFoundException e) {
            assertThat(e.getMessage()).contains("missingno");
        }
        verify(pokemonClient).getPokemonByName("missingno");
        verifyNoInteractions(pokemonMapper, translationService);
    }

    @Test
    void shouldThrowExternalApiException_WhenExceptionIsThrown() {
        // given
        when(pokemonClient.getPokemonByName("error")).thenThrow(new RuntimeException("API error"));
        // when & then
        try {
            pokemonService.getPokemonInfo("error");
            fail("Expected ExternalApiException");
        } catch (com.truelayer.interview.funpokedex.exception.ExternalApiException e) {
            assertThat(e.getMessage()).contains("Failed to retrieve Pokemon information");
        }
        verify(pokemonClient).getPokemonByName("error");
        verifyNoInteractions(pokemonMapper, translationService);
    }

    @Test
    void shouldThrowPokemonNotFoundException_WhenGetPokemonInfoFailsInTranslation() {
        // given
        when(pokemonClient.getPokemonByName("ghost")).thenReturn(null);
        // when & then
        try {
            pokemonService.getTranslatedPokemonInfo("ghost");
            fail("Expected PokemonNotFoundException");
        } catch (com.truelayer.interview.funpokedex.exception.PokemonNotFoundException e) {
            assertThat(e.getMessage()).contains("ghost");
        }
        verify(pokemonClient).getPokemonByName("ghost");
        verifyNoInteractions(translationService);
    }

    @Test
    void shouldHandleExceptionDuringTranslation() {
        // given
        when(pokemonClient.getPokemonByName("psyduck")).thenReturn(apiResponse);
        when(pokemonMapper.toDomain(apiResponse)).thenReturn(mappedResponse);
        doThrow(new RuntimeException("Translation error")).when(translationService).translate(mappedResponse);
        // when
        PokemonResponse result = pokemonService.getTranslatedPokemonInfo("psyduck");
        // then
        assertThat(result).isEqualTo(mappedResponse);
        verify(translationService).translate(mappedResponse);
    }

    // Add more tests as needed to cover edge cases and error handling

}
