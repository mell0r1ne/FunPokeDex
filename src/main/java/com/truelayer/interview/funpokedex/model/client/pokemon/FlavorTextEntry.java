package com.truelayer.interview.funpokedex.model.client.pokemon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlavorTextEntry {
    private String flavorText;
    private Language language;
}
