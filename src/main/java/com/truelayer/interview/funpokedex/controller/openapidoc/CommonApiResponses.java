package com.truelayer.interview.funpokedex.controller.openapidoc;

import io.swagger.v3.oas.annotations.media.Content;
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
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
        @ApiResponse(responseCode = "404", description = "Pokemon not found",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)),
        @ApiResponse(responseCode = "500", description = "Internal server error",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
})
public @interface CommonApiResponses {
}
