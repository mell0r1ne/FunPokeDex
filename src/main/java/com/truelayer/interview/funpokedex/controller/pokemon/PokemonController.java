package com.truelayer.interview.funpokedex.controller.pokemon;

import com.truelayer.interview.funpokedex.controller.ApiRoutes;
import com.truelayer.interview.funpokedex.controller.openapidoc.CommonApiResponses;
import com.truelayer.interview.funpokedex.model.dto.PokemonResponse;
import com.truelayer.interview.funpokedex.validator.ValidPokemonName;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Tag(name = "Pokemon Controller", description = "API endpoints for retrieving Pokemon information with optional fun translations")
public interface PokemonController {

    @Operation(
            summary = "Get Pokemon information",
            description = "Retrieves basic information about a Pokemon by its name including description, habitat, and legendary status."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved Pokemon information",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PokemonResponse.class),
                            examples = @ExampleObject(
                                    name = "Bulbasaur Example",
                                    value = """
                                            {
                                                "name": "bulbasaur",
                                                "description": "A strange seed was planted on its back at birth...",
                                                "habitat": "grassland",
                                                "legendary": false
                                            }
                                            """
                            )
                    )
            ),
    })
    @CommonApiResponses
    @GetMapping(ApiRoutes.PokemonRoutes.GET_INFO)
    ResponseEntity<PokemonResponse> getPokemonInfo(@PathVariable @ValidPokemonName String name);

    @Operation(
            summary = "Get translated Pokemon information",
            description = "Retrieves Pokemon information with fun translated descriptions..."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successfully retrieved translated Pokemon information",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PokemonResponse.class),
                            examples = {
                                    @ExampleObject(name = "Shakespeare Translation", value = "{...}"),
                                    @ExampleObject(name = "Yoda Translation", value = "{...}"),
                                    @ExampleObject(name = "Fallback Original", value = "{...}")
                            }
                    )
            )
    })
    @CommonApiResponses
    @GetMapping(ApiRoutes.PokemonRoutes.GET_TRANSLATED)
    ResponseEntity<PokemonResponse> getTranslatedPokemonInfo(
            @Parameter(description = "The name of the Pokemon", required = true, example = "bulbasaur")
            @PathVariable @ValidPokemonName String name);
}
