package com.truelayer.interview.funpokedex.model.client.pokemon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlavorTextEntry {
    @JsonProperty("flavor_text")
    private String flavorText;
    private Language language;
}
