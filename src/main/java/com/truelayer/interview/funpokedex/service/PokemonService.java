package com.truelayer.interview.funpokedex.service;

import com.truelayer.interview.funpokedex.client.pokemon.PokemonClient;
import com.truelayer.interview.funpokedex.exception.ExternalApiException;
import com.truelayer.interview.funpokedex.exception.PokemonNotFoundException;
import com.truelayer.interview.funpokedex.mapper.PokemonMapper;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.model.client.pokemon.PokemonApiResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

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
     * @return the mapped PokemonResponse
     * @throws PokemonNotFoundException if the Pokemon is not found
     * @throws ExternalApiException if external API call fails
     */
    public PokemonResponse getPokemonInfo(@NotBlank String name) {
        try {
            PokemonApiResponse apiResponse = pokemonClient.getPokemonByName(name);
            if (apiResponse == null) {
                log.debug("Pokemon '{}' not found - API returned null", name);
                throw new PokemonNotFoundException("Pokemon '" + name + "' not found");
            }
            return pokemonMapper.toDomain(apiResponse);
        } catch (HttpClientErrorException.NotFound e) {
            log.debug("Pokemon '{}' not found", name);
            throw new PokemonNotFoundException("Pokemon '" + name + "' not found", e);
        } catch (PokemonNotFoundException e) {
            throw e;
        } catch (Exception e) {
            log.error("Failed to retrieve pokemon '{}': {}", name, e.getMessage(), e);
            throw new ExternalApiException("Failed to retrieve Pokemon information", e);
        }
    }

    /**
     * Retrieves Pokémon information and translates its description.
     * @param name the name of the Pokémon
     * @return the translated PokemonResponse
     * @throws PokemonNotFoundException if the Pokemon is not found
     * @throws ExternalApiException if external API call fails
     */
    public PokemonResponse getTranslatedPokemonInfo(@NotBlank String name) {
        PokemonResponse pokemon = getPokemonInfo(name);
        try {
            translationService.translate(pokemon);
            log.debug("Successfully translated pokemon '{}'.", pokemon.getName());
        } catch (Exception e) {
            // Translation failures are non-critical - log warning but return untranslated pokemon
            log.warn("Failed to translate pokemon '{}', returning with original description: {}",
                    pokemon.getName(), e.getMessage());
        }
        return pokemon;
    }
}
