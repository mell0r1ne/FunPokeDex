package com.truelayer.interview.funpokedex.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(
    description = "Available translation types for Pokemon descriptions",
    example = "yoda",
    allowableValues = {"yoda", "shakespeare"}
)
public enum TranslatorType {
    @Schema(description = "Translate text to Yoda speak style")
    YODA("yoda"),
    
    @Schema(description = "Translate text to Shakespeare speak style")  
    SHAKESPEARE("shakespeare");

    private final String authorName;

    TranslatorType(String authorName) {
        this.authorName = authorName;
    }
}
