package br.com.compass.search.user;

import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResultDTO;
import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCreditsDTO;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.Params;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieProvidersDTO;
import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchByDTO;
import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiSearchByActorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "movieSearch", url = "https://api.themoviedb.org/3/")
public interface MovieSearch {

    @GetMapping(value = "search/movie")
    ResponseApiSearchByDTO getMovieByName(@SpringQueryMap ParamsSearchByName searchByName);

    @GetMapping(value = "discover/movie")
    ResponseApiSearchByDTO getMovieByFilters(@SpringQueryMap ParamsSearchByFilters searchByFilters, @RequestParam(name = "primary_release_date.gte", required = false) String releaseDateAfter, @RequestParam(name = "primary_release_date.lte", required = false) String releaseDateBefore);
    @GetMapping(value = "/movie/{movieId}/recommendations")
    ResponseApiSearchByDTO getMovieByRecommendations(@SpringQueryMap ParamsSearchByRecommendations byRecommendations, @PathVariable("movieId") Long movieId);

    @GetMapping(value = "search/person")
    ResponseApiSearchByActorDTO getMoviesByActors(@SpringQueryMap ParamsSearchByName searchByName);

    @GetMapping(value = "/movie/{movieId}/watch/providers")
    ResponseApiMovieProvidersDTO getMovieWatchProviders(@SpringQueryMap Params params, @PathVariable("movieId") Long movieId);

    @GetMapping(value = "/movie/{movieId}/credits")
    ResponseApiMovieCreditsDTO getMovieCredits(@SpringQueryMap Params params, @PathVariable("movieId") Long movieId);

    @GetMapping(value = "movie/{movieId}")
    ResponseApiResultDTO getMovieById(@SpringQueryMap Params params, @PathVariable("movieId") Long movieId);
}
