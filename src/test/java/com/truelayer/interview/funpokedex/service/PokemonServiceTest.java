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

}
