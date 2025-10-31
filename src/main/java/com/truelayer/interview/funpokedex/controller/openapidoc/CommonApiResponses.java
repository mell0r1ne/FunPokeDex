package com.truelayer.interview.funpokedex.controller.openapidoc;

import com.truelayer.interview.funpokedex.model.dto.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
        @ApiResponse(responseCode = "400", description = "Bad request - Invalid Pokemon name format",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = {
                            @ExampleObject(
                                name = "InvalidName",
                                summary = "Pokemon name format invalid",
                                value = """
                                {
                                    "error": "Bad Request",
                                    "message": "Invalid Pokemon name format",
                                    "timestamp": "2025-10-31T12:00:00",
                                    "status": "400",
                                    "path": "/pokemon/123invalid"
                                }
                                """
                            )
                        }
                )
        ),
        @ApiResponse(responseCode = "404", description = "Pokemon not found",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = {
                            @ExampleObject(
                                name = "NotFound",
                                summary = "Pokemon not found in PokeAPI",
                                value = """
                                {
                                    "error": "Pokemon not found",
                                    "message": "The requested Pokemon was not found in the PokeAPI database",
                                    "timestamp": "2025-10-31T12:05:00",
                                    "status": "404",
                                    "path": "/pokemon/bulbasaur"
                                }
                                """
                            )
                        }
                )
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = ErrorResponse.class),
                        examples = {
                            @ExampleObject(
                                name = "InternalError",
                                summary = "Unexpected server error",
                                value = """
                                {
                                    "error": "Internal Server Error",
                                    "message": "An unexpected error occurred. Please try again later.",
                                    "timestamp": "2025-10-31T12:10:00",
                                    "status": "500",
                                    "path": "/pokemon/charmander"
                                }
                                """
                            )
                        }
                )
        )
})
public @interface CommonApiResponses {
}

