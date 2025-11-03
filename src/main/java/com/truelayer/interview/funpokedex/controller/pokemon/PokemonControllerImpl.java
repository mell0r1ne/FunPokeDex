package com.truelayer.interview.funpokedex.controller.pokemon;

import com.truelayer.interview.funpokedex.controller.ApiRoutes;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.service.PokemonService;
import com.truelayer.interview.funpokedex.service.UtilService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiRoutes.PokemonRoutes.BASE)
@RequiredArgsConstructor
@Validated
public class PokemonControllerImpl implements PokemonController {

    private final PokemonService pokemonService;

    /**
     * Returns basic Pokémon information by name.
     * Validates input and returns 400 for invalid names, 404 if not found.
     * @param name the name of the Pokémon
     * @return ResponseEntity with Pokémon info or error status
     */
    @GetMapping(ApiRoutes.PokemonRoutes.GET_INFO)
    @Override
    public ResponseEntity<PokemonResponse> getPokemonInfo(@RequestParam String name) {
        if (isInvalidName(name)) {
            return ResponseEntity.badRequest().build();
        }
        PokemonResponse response = pokemonService.getPokemonInfo(name.toLowerCase().trim());
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Returns translated Pokémon information by name.
     * Validates input and returns 400 for invalid names, 404 if not found.
     * @param name the name of the Pokémon
     * @return ResponseEntity with translated Pokémon info or error status
     */
    @GetMapping(ApiRoutes.PokemonRoutes.GET_TRANSLATED)
    @Override
    public ResponseEntity<PokemonResponse> getTranslatedPokemonInfo(@RequestParam String name) {
        if (isInvalidName(name)) {
            return ResponseEntity.badRequest().build();
        }
        PokemonResponse response = pokemonService.getTranslatedPokemonInfo(name.toLowerCase().trim());
        if (response == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

    private boolean isInvalidName(String name) {
        return name == null || name.isBlank();
    }

}
