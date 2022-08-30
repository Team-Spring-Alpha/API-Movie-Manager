package br.com.compass.search.dto.apiTheMoviedb.movieParams;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @Setter(AccessLevel.PUBLIC)
    private List<Long> with_people;

    public ParamsSearchByFilters(String apiKey) {
        super(apiKey);
    }
}
