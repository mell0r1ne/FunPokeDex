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
import static org.junit.jupiter.api.Assertions.fail;
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
    void shouldReturnBadRequest_WhenNameIsNull() {
        ResponseEntity<PokemonResponse> response = controller.getPokemonInfo(null);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        verifyNoInteractions(pokemonService);
    }

    @Test
    void shouldReturnBadRequest_WhenNameIsBlank() {
        ResponseEntity<PokemonResponse> response = controller.getPokemonInfo("   ");
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        verifyNoInteractions(pokemonService);
    }

    @Test
    void shouldReturnNotFound_WhenPokemonNotFound() {
        when(pokemonService.getPokemonInfo("pikachu")).thenReturn(null);
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
    void shouldPropagateException_OnException() {
        when(pokemonService.getPokemonInfo("pikachu")).thenThrow(new RuntimeException("fail"));
        try {
            controller.getPokemonInfo("pikachu");
            fail("Exception was expected");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).isEqualTo("fail");
        }
    }

    @Test
    void shouldReturnNotFound_WhenTranslatedPokemonNotFound() {
        when(pokemonService.getTranslatedPokemonInfo("pikachu")).thenReturn(null);
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
    void shouldPropagateException_OnTranslatedException() {
        when(pokemonService.getTranslatedPokemonInfo("pikachu")).thenThrow(new RuntimeException("fail"));
        try {
            controller.getTranslatedPokemonInfo("pikachu");
            fail("Exception was expected");
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).isEqualTo("fail");
        }
    }

    @Test
    void shouldReturnBadRequest_WhenTranslatedNameIsNull() {
        ResponseEntity<PokemonResponse> response = controller.getTranslatedPokemonInfo(null);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        verifyNoInteractions(pokemonService);
    }

    @Test
    void shouldReturnBadRequest_WhenTranslatedNameIsBlank() {
        ResponseEntity<PokemonResponse> response = controller.getTranslatedPokemonInfo("   ");
        assertThat(response.getStatusCode().value()).isEqualTo(400);
        verifyNoInteractions(pokemonService);
    }
}
