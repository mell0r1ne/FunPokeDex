package com.truelayer.interview.funpokedex.mapper;

import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.model.client.pokemon.PokemonApiResponse;
import com.truelayer.interview.funpokedex.model.enums.Languages;
import org.springframework.stereotype.Component;

@Component
public class PokemonMapper {

    public PokemonResponse toDomain(PokemonApiResponse apiResponse) {
        return PokemonResponse.builder()
                .name(apiResponse.getName())
                .description(extractEnglishDescription(apiResponse))
                .habitat(apiResponse.getHabitat() != null ? apiResponse.getHabitat().getName() : "")
                .isLegendary(apiResponse.isLegendary())
                .build();
    }

    private String extractEnglishDescription(PokemonApiResponse apiResponse) {
        if (apiResponse.getFlavorTextEntries() == null) return "";

        return apiResponse.getFlavorTextEntries().stream()
                //improvement for prod: add a header in the request to get the description in the corresponding language
                .filter(entry -> entry.getLanguage() != null && Languages.ENGLISH.getLanguageCode().equalsIgnoreCase(entry.getLanguage().getName()))
                .findFirst()
                .map(entry -> sanitize(entry.getFlavorText()))
                .orElse("");
    }

    private String sanitize(String text) {
        return text == null ? "" : text.replaceAll("\\s+", " ").trim();
    }
}
