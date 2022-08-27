package br.com.compass.search.dto.apiTheMoviedb.movieParams;

import lombok.Getter;

@Getter
public class ParamsSearchByRecommendations extends Params{

    private int page = 1;

    public ParamsSearchByRecommendations(String apiKey) {
        super.setApi_key(apiKey);
    }
}
