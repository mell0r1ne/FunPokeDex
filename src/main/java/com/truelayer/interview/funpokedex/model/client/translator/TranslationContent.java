package com.truelayer.interview.funpokedex.model.client.translator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TranslationContent {
    private String text;
    private String translated;

}
