package br.com.compass.search.dto.apiTheMoviedb.movieParams;

import lombok.Getter;

@Getter
public class ParamsSearchByFilters extends Params{
    private boolean include_adult = false;
    private int page = 1;
    private String watch_region = "BR";
    private Long with_genres;
    private Long with_watch_providers;

    public ParamsSearchByFilters(String api_key, Long with_genres, Long with_watch_providers) {
        super(api_key);
        this.with_genres = with_genres;
        this.with_watch_providers = with_watch_providers;
    }
}
