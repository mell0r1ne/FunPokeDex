package com.truelayer.interview.funpokedex.client.translator;

import com.truelayer.interview.funpokedex.model.client.pokemon.PokemonApiResponse;
import com.truelayer.interview.funpokedex.model.client.translator.TranslationApiResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange(accept = MediaType.APPLICATION_JSON_VALUE)
public interface TranslationClient {

    @PostExchange(url = "/{authorName}.json", contentType = "application/x-www-form-urlencoded")
    TranslationApiResponse getTranslation(@PathVariable("authorName") String authorName, @RequestParam("text") String text);

}
