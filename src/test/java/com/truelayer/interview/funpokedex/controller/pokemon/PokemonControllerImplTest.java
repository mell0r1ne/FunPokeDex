package com.truelayer.interview.funpokedex.controller.pokemon;

import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.service.PokemonService;
import com.truelayer.interview.funpokedex.service.UtilService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PokemonControllerImplTest {

    @Mock
    private PokemonService pokemonService;
    @Mock
    private UtilService utilService;
    @InjectMocks
    private PokemonControllerImpl controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(utilService.sanitizeForLogging(anyString())).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void shouldReturnNotFound_WhenPokemonNotFound() {
        when(pokemonService.getPokemonInfo("pikachu"))
                .thenThrow(new com.truelayer.interview.funpokedex.exception.PokemonNotFoundException("Pokemon 'pikachu' not found"));
        ResponseEntity<PokemonResponse> response = controller.getPokemonInfo("pikachu");
        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void shouldReturnOk_WhenPokemonFound() {
        PokemonResponse poke = PokemonResponse.builder().name("pikachu").build();
        when(pokemonService.getPokemonInfo("pikachu")).thenReturn(poke);
        ResponseEntity<PokemonResponse> response = controller.getPokemonInfo("pikachu");
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(poke);
    }

    @Test
    void shouldReturnServiceUnavailable_WhenExternalApiException() {
        when(pokemonService.getPokemonInfo("pikachu"))
                .thenThrow(new com.truelayer.interview.funpokedex.exception.ExternalApiException("API error"));
        ResponseEntity<PokemonResponse> response = controller.getPokemonInfo("pikachu");
        assertThat(response.getStatusCode().value()).isEqualTo(503);
    }

    @Test
    void shouldReturnNotFound_WhenTranslatedPokemonNotFound() {
        when(pokemonService.getTranslatedPokemonInfo("pikachu"))
                .thenThrow(new com.truelayer.interview.funpokedex.exception.PokemonNotFoundException("Pokemon 'pikachu' not found"));
        ResponseEntity<PokemonResponse> response = controller.getTranslatedPokemonInfo("pikachu");
        assertThat(response.getStatusCode().value()).isEqualTo(404);
    }

    @Test
    void shouldReturnOk_WhenTranslatedPokemonFound() {
        PokemonResponse poke = PokemonResponse.builder().name("pikachu").build();
        when(pokemonService.getTranslatedPokemonInfo("pikachu")).thenReturn(poke);
        ResponseEntity<PokemonResponse> response = controller.getTranslatedPokemonInfo("pikachu");
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isEqualTo(poke);
    }


    @Test
    void shouldReturnServiceUnavailable_WhenTranslatedExternalApiException() {
        when(pokemonService.getTranslatedPokemonInfo("mewtwo"))
                .thenThrow(new com.truelayer.interview.funpokedex.exception.ExternalApiException("API error"));
        ResponseEntity<PokemonResponse> response = controller.getTranslatedPokemonInfo("mewtwo");
        assertThat(response.getStatusCode().value()).isEqualTo(503);
    }
}
