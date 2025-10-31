package com.truelayer.interview.funpokedex.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        description = "Error response containing details about what went wrong",
        example = """
        {
            "error": "Pokemon not found",
            "message": "The requested Pokemon was not found in the PokeAPI database",
            "timestamp": "2025-10-31T12:00:00",
            "status": "404",
            "path": "/pokemon/bulbasaur"
        }
    """
)
public class ErrorResponse {

    @Schema(
            description = "Brief error description",
            example = "Pokemon not found"
    )
    private String error;

    @Schema(
            description = "Detailed error message explaining what went wrong",
            example = "The requested Pokemon was not found in the PokeAPI database"
    )
    private String message;

    @Schema(
            description = "Timestamp when the error occurred in ISO-8601 format",
            example = "2025-10-31T12:00:00"
    )
    private String timestamp;

    @Schema(
            description = "HTTP status code returned for the error",
            example = "404"
    )
    private String status;

    @Schema(
            description = "Path of the request that caused the error",
            example = "/pokemon/bulbasaur"
    )
    private String path;
}