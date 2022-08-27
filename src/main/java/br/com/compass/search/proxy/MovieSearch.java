package br.com.compass.search.proxy;

import br.com.compass.search.dto.apiTheMoviedb.movieParams.Params;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchBy;
import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiSearchByActor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "movieSearch", url = "https://api.themoviedb.org/3/")
public interface MovieSearch {

    @GetMapping(value = "search/movie")
    ResponseApiSearchBy getMovieByName(@SpringQueryMap ParamsSearchByName searchByName);

    @GetMapping(value = "discover/movie")
    ResponseApiSearchBy getMovieByFilters(@SpringQueryMap ParamsSearchByFilters searchByFilters);

    @GetMapping(value = "/movie/{movieId}/recommendations")
    ResponseApiSearchBy getMovieByRecommendations(@SpringQueryMap ParamsSearchByRecommendations byRecommendations, @PathVariable("movieId") Long movieId);

    @GetMapping(value = "search/person")
    ResponseApiSearchByActor getMoviesByActors(@SpringQueryMap ParamsSearchByName searchByName);
}
