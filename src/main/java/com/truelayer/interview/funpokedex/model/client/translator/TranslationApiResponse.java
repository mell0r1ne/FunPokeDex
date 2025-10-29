package com.truelayer.interview.funpokedex.model.client.translator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranslationApiResponse {
    private TranslationContent content;
}
