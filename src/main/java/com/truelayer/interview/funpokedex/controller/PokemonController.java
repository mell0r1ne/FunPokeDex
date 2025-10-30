package com.truelayer.interview.funpokedex.controller;

import com.truelayer.interview.funpokedex.config.ApiRoutes;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiRoutes.BASE_POKEMON)
@RequiredArgsConstructor
public class PokemonController {

    private final PokemonService pokemonService;

    @GetMapping(ApiRoutes.Pokemon.GET_BY_NAME)
    public ResponseEntity<PokemonResponse> getPokemon(@PathVariable String name) {
        return ResponseEntity.ok(pokemonService.getPokemonInfo(name));
    }

    @GetMapping(ApiRoutes.Pokemon.GET_TRANSLATED)
    public ResponseEntity<PokemonResponse> getTranslatedPokemon(@PathVariable String name) {
        return ResponseEntity.ok(pokemonService.getTranslatedPokemonInfo(name));
    }
}
