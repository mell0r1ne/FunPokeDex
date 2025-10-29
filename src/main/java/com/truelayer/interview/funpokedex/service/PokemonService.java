package com.truelayer.interview.funpokedex.service;

import com.truelayer.interview.funpokedex.client.PokemonClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PokemonService {
    private final PokemonClient pokemonClient;
    private final TranslationService translationService;

}
