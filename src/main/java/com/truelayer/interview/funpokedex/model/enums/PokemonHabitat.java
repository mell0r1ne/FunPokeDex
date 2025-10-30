package com.truelayer.interview.funpokedex.model.enums;

import lombok.Getter;

@Getter
public enum PokemonHabitat {
    CAVE("cave");
    //Add other habitats for comprehensive handling.


    private final String habitat;

    PokemonHabitat(String habitat) {
        this.habitat = habitat;
    }
}
