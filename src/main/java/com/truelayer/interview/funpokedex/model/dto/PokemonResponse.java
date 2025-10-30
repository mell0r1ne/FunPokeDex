package com.truelayer.interview.funpokedex.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PokemonResponse {
    private String name;
    private String description;
    private String habitat;
    private boolean isLegendary;
}
