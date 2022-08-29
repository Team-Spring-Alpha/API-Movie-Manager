package br.com.compass.search.dto.apiTheMoviedb.movieParams;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class ParamsSearchByRecommendations extends Params{

    private int page = 1;

    public ParamsSearchByRecommendations(String apiKey) {
        super(apiKey);
    }
}
