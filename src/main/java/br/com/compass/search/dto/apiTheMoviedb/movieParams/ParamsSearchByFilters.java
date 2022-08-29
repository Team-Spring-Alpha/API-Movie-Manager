package br.com.compass.search.dto.apiTheMoviedb.movieParams;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.NONE)
@EqualsAndHashCode

public class ParamsSearchByFilters extends Params{
    private boolean include_adult = false;
    private int page = 1;
    private String watch_region = "BR";
    @Setter(AccessLevel.PUBLIC)
    private Long with_genres;
    @Setter(AccessLevel.PUBLIC)
    private Long with_watch_providers;

    public ParamsSearchByFilters(String apiKey) {
        super(apiKey);
    }
}
