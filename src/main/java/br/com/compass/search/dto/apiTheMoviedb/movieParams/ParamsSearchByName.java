package br.com.compass.search.dto.apiTheMoviedb.movieParams;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ParamsSearchByName extends Params{
    private String query;
    private int page = 1;
    @JsonProperty("include_adult")
    private boolean include_adult = false;

    public ParamsSearchByName(String apiKey, String movieName) {
        this.query = movieName;
        super.setApi_key(apiKey);
    }
}
