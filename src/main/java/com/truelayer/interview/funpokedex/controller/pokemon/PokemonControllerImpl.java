package com.truelayer.interview.funpokedex.controller.pokemon;

import com.truelayer.interview.funpokedex.config.ApiRoutes;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.service.PokemonService;
import com.truelayer.interview.funpokedex.service.UtilService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(ApiRoutes.Pokemon.BASE_POKEMON)
@RequiredArgsConstructor
@Slf4j
@Validated
public class PokemonControllerImpl implements PokemonController {

    private final PokemonService pokemonService;
    private final UtilService utilService;

    @GetMapping(ApiRoutes.Pokemon.GET_INFO)
    @Override
    public ResponseEntity<PokemonResponse> getPokemonInfo(String name) {
        
        log.info("Fetching Pokemon info for: {}", utilService.sanitizeForLogging(name));
        return ResponseEntity.ok(pokemonService.getPokemonInfo(name.toLowerCase().trim()));
    }


    @GetMapping(ApiRoutes.Pokemon.GET_TRANSLATED)
    @Override
    public ResponseEntity<PokemonResponse> getTranslatedPokemonInfo(String name) {
        
        log.info("Fetching translated Pokemon info for: {}", utilService.sanitizeForLogging(name));
        return ResponseEntity.ok(pokemonService.getTranslatedPokemonInfo(name.toLowerCase().trim()));
    }

}
