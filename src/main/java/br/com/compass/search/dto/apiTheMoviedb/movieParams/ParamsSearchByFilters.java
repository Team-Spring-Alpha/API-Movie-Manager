package br.com.compass.search.dto.apiTheMoviedb.movieParams;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode
public class ParamsSearchByFilters extends Params{
    private final boolean include_adult = false;
    private final int page = 1;
    private final String watch_region = "BR";
    private final Long with_genres;
    private final Long with_watch_providers;
    private final List<Long> with_people;

    public ParamsSearchByFilters(String api_key, Long with_genres, Long with_watch_providers, List<Long> with_people) {
        super(api_key);
        this.with_genres = with_genres;
        this.with_watch_providers = with_watch_providers;
        this.with_people = with_people;
    }
}
