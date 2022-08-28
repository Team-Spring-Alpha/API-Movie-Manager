package br.com.compass.search.service;

import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiclient.response.ResponseApiClient;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import br.com.compass.search.proxy.MovieSearchProxy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final MovieSearchProxy movieSearchProxy;

    @Value("${API_KEY}")
    private String apiKey;

    public List<ResponseApiClient> findByName(String movieName) {
        ParamsSearchByName searchByName = new ParamsSearchByName(apiKey, movieName);
        return movieSearchProxy.getMovieSearchByName(searchByName);
    }

    public List<ResponseApiClient> findMoviesRecommendations(Long movieId) {
        ParamsSearchByRecommendations searchByRecommendations = new ParamsSearchByRecommendations(apiKey);
        return movieSearchProxy.getMovieByRecommendation(searchByRecommendations, movieId);
    }
    public List<ResponseApiClient> findByActor(String movieActor){
        ParamsSearchByName searchByName = new ParamsSearchByName(apiKey, movieActor);
        return movieSearchProxy.getMovieByActorName(searchByName);
    }

    public List<ResponseApiClient> findByFilters(GenresEnum movieGenre, LocalDate dateGte, LocalDate dateLte, ProvidersEnum movieProvider) {
        ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(apiKey, movieGenre.getIdGenrer(), movieProvider.getIdProvider());

        String dateAfterString = null;
        String dateBeforeString = null;
        if (dateGte != null) {
            dateAfterString = dateGte.toString();
        }
        if (dateLte != null) {
            dateBeforeString = dateLte.toString();
        }

        return movieSearchProxy.getMovieSearchByFilters(searchByFilters, dateAfterString, dateBeforeString);
    }
}
