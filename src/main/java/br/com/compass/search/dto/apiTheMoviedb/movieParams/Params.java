package br.com.compass.search.dto.apiTheMoviedb.movieParams;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Params {
    @JsonProperty("api_key")
    @Setter(AccessLevel.PROTECTED)
    private String api_key;
    private String language = "pt-BR";
}
