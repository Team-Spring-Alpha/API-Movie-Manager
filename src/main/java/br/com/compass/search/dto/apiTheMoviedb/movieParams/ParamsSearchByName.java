package br.com.compass.search.dto.apiTheMoviedb.movieParams;

import lombok.Getter;

@Getter
public class ParamsSearchByName extends Params{
    private String query;
    private int page = 1;
    private boolean include_adult = false;

    public ParamsSearchByName(String apiKey, String movieName) {
        super(apiKey);
        this.query = movieName;
    }
}
