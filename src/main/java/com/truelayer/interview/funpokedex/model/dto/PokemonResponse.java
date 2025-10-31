package com.truelayer.interview.funpokedex.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
    description = "Pokemon information response containing basic details about a Pokemon",
    example = "{\"name\":\"mewtwo\",\"description\":\"It was created by a scientist after years of horrific gene splicing and DNA engineering experiments.\",\"habitat\":\"rare\",\"isLegendary\":true}"
)
public class PokemonResponse {
    @Schema(
        description = "The name of the Pokemon in lowercase",
        example = "mewtwo",
        required = true
    )
    private String name;
    
    @Schema(
        description = "A description of the Pokemon, potentially translated to Yoda or Shakespeare speak if requested",
        example = "It was created by a scientist after years of horrific gene splicing and DNA engineering experiments.",
        required = true
    )
    private String description;
    
    @Schema(
        description = "The habitat where this Pokemon can typically be found",
        example = "rare",
        allowableValues = {"cave", "forest", "grassland", "mountain", "rare", "rough-terrain", "sea", "urban", "waters-edge"},
        required = true
    )
    private String habitat;
    
    @Schema(
        description = "Indicates whether this Pokemon is classified as legendary",
        example = "true",
        required = true
    )
    private boolean isLegendary;
}
