package com.truelayer.interview.funpokedex.service;

import com.truelayer.interview.funpokedex.client.pokemon.PokemonClient;
import com.truelayer.interview.funpokedex.mapper.PokemonMapper;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.model.client.pokemon.PokemonApiResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PokemonService {

    private final PokemonClient pokemonClient;
    private final TranslationService translationService;
    private final PokemonMapper pokemonMapper;

    /**
     * Retrieves Pokémon information by name.
     * @param name the name of the Pokémon
     * @return the mapped PokemonResponse, or null if not found or input is invalid
     */
    public PokemonResponse getPokemonInfo(@NotBlank String name) {
        if (name == null || name.isBlank()) {
            log.warn("Pokemon name is null or blank");
            return null;
        }
        try {
            PokemonApiResponse apiResponse = pokemonClient.getPokemonByName(name);
            if (apiResponse == null) {
                log.warn("No API response for pokemon: {}", name);
                return null;
            }
            return pokemonMapper.toDomain(apiResponse);
        } catch (Exception e) {
            log.warn("Exception while retrieving pokemon '{}': {}", name, e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves Pokémon information and translates its description.
     * @param name the name of the Pokémon
     * @return the translated PokemonResponse, or null if not found or input is invalid
     */
    public PokemonResponse getTranslatedPokemonInfo(@NotBlank String name) {
        PokemonResponse pokemon = getPokemonInfo(name);
        if (pokemon == null) {
            log.warn("Cannot translate: pokemon is null for name '{}'.", name);
            return null;
        }
        try {
            translationService.translate(pokemon);
            log.debug("Successfully translated pokemon '{}'.", pokemon.getName());
        } catch (Exception e) {
            String pokemonName = pokemon.getName() != null ? pokemon.getName() : "unknown";
            log.warn("Failed to translate '{}': {}", pokemonName, e.getMessage());
        }
        return pokemon;
    }
}
