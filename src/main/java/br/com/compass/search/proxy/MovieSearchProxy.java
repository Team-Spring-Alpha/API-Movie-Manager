package br.com.compass.search.proxy;

import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchBy;
import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiSearchByActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieSearchProxy {

    @Autowired
    private MovieSearch movieSearch;

    public ResponseApiSearchBy getMovieSearchByName(ParamsSearchByName searchByName) {
        return movieSearch.getMovieByName(searchByName);
    }

    public ResponseApiSearchBy getMovieSearchByFilters(ParamsSearchByFilters searchByFilters) {
        return movieSearch.getMovieByFilters(searchByFilters);
    }

    public ResponseApiSearchBy getMovieByRecommendation(ParamsSearchByRecommendations byRecommendations, Long movieId) {
        return movieSearch.getMovieByRecommendations(byRecommendations, movieId);
    }

    public ResponseApiSearchByActor getMovieByActorName(ParamsSearchByName searchByName) {
        return movieSearch.getMoviesByActors(searchByName);
    }
}
