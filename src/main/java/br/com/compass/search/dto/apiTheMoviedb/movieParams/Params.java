package br.com.compass.search.dto.apiTheMoviedb.movieParams;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class Params {
    private final String api_key;
    private final String language = "pt-BR";

    public Params(String api_key) {
        this.api_key = api_key;
    }
}
