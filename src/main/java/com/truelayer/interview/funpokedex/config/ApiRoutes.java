package com.truelayer.interview.funpokedex.config;

public final class ApiRoutes {

    private ApiRoutes() {
        // Utility class
    }


    public static final class Pokemon {
        private Pokemon() {}
        public static final String BASE_POKEMON = "/pokemon";
        public static final String GET_INFO = "/{name}";
        public static final String GET_TRANSLATED = "/translated/{name}";
    }
}
