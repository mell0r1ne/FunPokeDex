package com.truelayer.interview.funpokedex.client.translator;

import com.truelayer.interview.funpokedex.model.client.pokemon.PokemonApiResponse;
import com.truelayer.interview.funpokedex.model.client.translator.TranslationApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

@Component
public interface TranslationClient {
    @GetExchange("/{authorName}")
    TranslationApiResponse getTranslation(@PathVariable String authorName, @RequestParam String text);
}
