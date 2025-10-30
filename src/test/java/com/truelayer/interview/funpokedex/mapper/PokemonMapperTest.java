package com.truelayer.interview.funpokedex.mapper;

import com.truelayer.interview.funpokedex.model.client.pokemon.FlavorTextEntry;
import com.truelayer.interview.funpokedex.model.client.pokemon.Habitat;
import com.truelayer.interview.funpokedex.model.client.pokemon.Language;
import com.truelayer.interview.funpokedex.model.client.pokemon.PokemonApiResponse;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link PokemonMapper}.
 */
class PokemonMapperTest {

    private PokemonMapper mapper;

    @BeforeEach
    void setup() {
        mapper = new PokemonMapper();
    }

    @Test
    void shouldMapBasicFieldsCorrectly() {
        // given
        PokemonApiResponse apiResponse = new PokemonApiResponse();
        apiResponse.setName("bulbasaur");
        apiResponse.setLegendary(false);

        Habitat habitat = new Habitat();
        habitat.setName("forest");
        apiResponse.setHabitat(habitat);

        apiResponse.setFlavorTextEntries(List.of(
                entry("A strange seed was planted on its back.", "en")
        ));

        // when
        PokemonResponse result = mapper.toDomain(apiResponse);

        // then
        assertThat(result.getName()).isEqualTo("bulbasaur");
        assertThat(result.getHabitat()).isEqualTo("forest");
        assertThat(result.isLegendary()).isFalse();
        assertThat(result.getDescription()).isEqualTo("A strange seed was planted on its back.");
    }

    @Test
    void shouldSanitizeTextByRemovingLineBreaksAndTabs() {
        // given
        String messyText = "A strange\nseed \fwas\tplanted  on its   back.";
        PokemonApiResponse apiResponse = new PokemonApiResponse();
        apiResponse.setFlavorTextEntries(List.of(entry(messyText, "en")));

        // when
        String result = mapper.toDomain(apiResponse).getDescription();

        // then
        assertThat(result).isEqualTo("A strange seed was planted on its back.");
    }

    @Test
    void shouldReturnEmptyDescription_WhenNoEnglishEntryFound() {
        // given
        PokemonApiResponse apiResponse = new PokemonApiResponse();
        apiResponse.setFlavorTextEntries(List.of(entry("Beschreibung", "de")));

        // when
        String result = mapper.toDomain(apiResponse).getDescription();

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyDescription_WhenFlavorTextEntriesIsNull() {
        // given
        PokemonApiResponse apiResponse = new PokemonApiResponse();
        apiResponse.setFlavorTextEntries(null);

        // when
        String result = mapper.toDomain(apiResponse).getDescription();

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnEmptyHabitat_WhenNull() {
        // given
        PokemonApiResponse apiResponse = new PokemonApiResponse();
        apiResponse.setName("mew");
        apiResponse.setHabitat(null);
        apiResponse.setFlavorTextEntries(List.of(entry("Psychic power unmatched.", "en")));

        // when
        PokemonResponse result = mapper.toDomain(apiResponse);

        // then
        assertThat(result.getHabitat()).isEmpty();
    }

    @Test
    void shouldReturnEmptyDescription_WhenTextIsNull() {
        // given
        PokemonApiResponse apiResponse = new PokemonApiResponse();
        apiResponse.setFlavorTextEntries(List.of(entry(null, "en")));

        // when
        String result = mapper.toDomain(apiResponse).getDescription();

        // then
        assertThat(result).isEmpty();
    }

    // --- helper ---
    private FlavorTextEntry entry(String text, String lang) {
        Language language = new Language();
        language.setName(lang);
        FlavorTextEntry entry = new FlavorTextEntry();
        entry.setLanguage(language);
        entry.setFlavorText(text);
        return entry;
    }
}
