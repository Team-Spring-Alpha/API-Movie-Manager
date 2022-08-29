package br.com.compass.search.dto.apiTheMoviedb.movieParams;

import lombok.Getter;

@Getter
public class Params {
    private String api_key;
    private String language = "pt-BR";

    public Params(String api_key) {
        this.api_key = api_key;
    }
}
