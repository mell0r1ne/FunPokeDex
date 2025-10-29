package com.truelayer.interview.funpokedex.model.client.pokemon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonApiResponse {
    private List<FlavorTextEntry> flavorTextEntries;
    private Habitat habitat;
    private String name;
    @JsonProperty("is_legendary")
    private boolean isLegendary;

}
