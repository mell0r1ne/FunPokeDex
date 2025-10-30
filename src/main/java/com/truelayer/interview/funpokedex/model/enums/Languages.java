package com.truelayer.interview.funpokedex.model.enums;

import lombok.Getter;

@Getter
public enum Languages {
    ENGLISH("en");
    //used for translation, extracted from code to enable future changes and avoid magic strings

    private final String languageCode;

    Languages(String languageCode) {
        this.languageCode = languageCode;
    }
}
