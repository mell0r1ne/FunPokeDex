package com.truelayer.interview.funpokedex.controller.pokemon;

import com.truelayer.interview.funpokedex.controller.ApiRoutes;
import com.truelayer.interview.funpokedex.exception.ExternalApiException;
import com.truelayer.interview.funpokedex.exception.PokemonNotFoundException;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.service.PokemonService;
import com.truelayer.interview.funpokedex.validator.ValidPokemonName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiRoutes.PokemonRoutes.BASE)
@RequiredArgsConstructor
@Validated
@Slf4j
public class PokemonControllerImpl implements PokemonController {

    private final PokemonService pokemonService;

    /**
     * Returns basic Pokémon information by name.
     * @param name the name of the Pokémon
     * @return ResponseEntity with Pokémon info or appropriate error status
     */
    @GetMapping(ApiRoutes.PokemonRoutes.GET_INFO)
    @Override
    public ResponseEntity<PokemonResponse> getPokemonInfo(@PathVariable @ValidPokemonName String name) {
        try {
            PokemonResponse response = pokemonService.getPokemonInfo(name.toLowerCase().trim());
            return ResponseEntity.ok(response);
        } catch (PokemonNotFoundException e) {
            log.debug("Pokemon '{}' not found", name);
            return ResponseEntity.notFound().build();
        } catch (ExternalApiException e) {
            log.error("External API error while fetching pokemon '{}'", name, e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

    /**
     * Returns translated Pokémon information by name.
     * @param name the name of the Pokémon
     * @return ResponseEntity with translated Pokémon info or appropriate error status
     */
    @GetMapping(ApiRoutes.PokemonRoutes.GET_TRANSLATED)
    @Override
    public ResponseEntity<PokemonResponse> getTranslatedPokemonInfo(@PathVariable @ValidPokemonName String name) {
        try {
            PokemonResponse response = pokemonService.getTranslatedPokemonInfo(name.toLowerCase().trim());
            return ResponseEntity.ok(response);
        } catch (PokemonNotFoundException e) {
            log.debug("Pokemon '{}' not found", name);
            return ResponseEntity.notFound().build();
        } catch (ExternalApiException e) {
            log.error("External API error while fetching translated pokemon '{}'", name, e);
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
        }
    }

}
