package com.truelayer.interview.funpokedex.model.enums;

import lombok.Getter;

@Getter
public enum TranslatorType {
    YODA("yoda"),
    SHAKESPEARE("shakespeare");

    private final String authorName;

    TranslatorType(String authorName) {
        this.authorName = authorName;
    }
}
