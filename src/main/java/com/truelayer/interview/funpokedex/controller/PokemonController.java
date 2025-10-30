package com.truelayer.interview.funpokedex.controller;

import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.service.PokemonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
@RequiredArgsConstructor
public class PokemonController {
    private final PokemonService pokemonService;

    @GetMapping("/{name}")
    public ResponseEntity<PokemonResponse> getPokemon(@PathVariable("name") String name) {
        return ResponseEntity.ok(pokemonService.getPokemonInfo(name));
    }

    @GetMapping("/translated/{name}")
    public ResponseEntity<PokemonResponse> getTranslatedPokemon(@PathVariable("name") String name) {
        return ResponseEntity.ok(pokemonService.getTranslatedPokemonInfo(name));
    }
}
