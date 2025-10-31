package com.truelayer.interview.funpokedex.controller;

public final class ApiRoutes {

    private ApiRoutes() {
    }


    public static final class PokemonRoutes {
        private PokemonRoutes() {}
        public static final String BASE = "/pokemon";
        public static final String GET_INFO = "/{name}";
        public static final String GET_TRANSLATED = "/translated/{name}";
    }
}
