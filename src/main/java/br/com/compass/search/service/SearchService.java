package br.com.compass.search.service;

import br.com.compass.search.client.MovieSearchProxy;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.Params;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiclient.response.ResponseApiClient;
import br.com.compass.search.dto.apiclient.response.ResponseApiClientMovieById;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final MovieSearchProxy movieSearchProxy;

    @Value("${API_KEY}")
    private String apiKey;

    public HashSet<ResponseApiClient> findMoviesRecommendations(Long movieId) {
        ParamsSearchByRecommendations searchByRecommendations = new ParamsSearchByRecommendations(apiKey);
        try{
            return movieSearchProxy.getMovieByRecommendation(searchByRecommendations, movieId);
        } catch (FeignException.FeignClientException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public HashSet<ResponseApiClient> findByFilters(GenresEnum movieGenre, LocalDate dateGte, LocalDate dateLte,
                                                    ProvidersEnum movieProvider, List<String> moviePeoples, String movieName) {
        HashSet<ResponseApiClient> movieSearchByName = new HashSet<>();
        HashSet<ResponseApiClient> movieSearchByFilters = new HashSet<>();

        if (movieName != null) {
            ParamsSearchByName searchByName = new ParamsSearchByName(apiKey, movieName);
            movieSearchByName = movieSearchProxy.getMovieSearchByName(searchByName);
        }

        movieSearchByFilters = searchByFilters(movieGenre, dateGte, dateLte, movieProvider, moviePeoples, movieName, movieSearchByFilters);

        if (!movieSearchByName.isEmpty()) {
            movieSearchByFilters.addAll(movieSearchByName);
        }

        return movieSearchByFilters;
    }

    private HashSet<ResponseApiClient> searchByFilters(GenresEnum movieGenre, LocalDate dateGte, LocalDate dateLte, ProvidersEnum movieProvider, List<String> moviePeoples, String movieName, HashSet<ResponseApiClient> movieSearchByFilters) {
        if (movieName == null || movieGenre != null || dateGte != null || dateLte != null || movieProvider != null || moviePeoples != null) {
            ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(apiKey);
            if (movieGenre != null) {
                searchByFilters.setWith_genres(movieGenre.getIdGenrer());
            }
            if (movieProvider != null) {
                searchByFilters.setWith_watch_providers(movieProvider.getIdProvider());
            }
            if (moviePeoples != null){
                List<Long> longs = movieSearchProxy.actorsStringToActorsId(moviePeoples);
                searchByFilters.setWith_people(longs);
            }

            String dateAfterString = null;
            String dateBeforeString = null;
            if (dateGte != null) {
                dateAfterString = dateGte.toString();
            }
            if (dateLte != null) {
                dateBeforeString = dateLte.toString();
            }

            movieSearchByFilters = movieSearchProxy.getMovieSearchByFilters(searchByFilters, dateAfterString, dateBeforeString);
        }
        return movieSearchByFilters;
    }

    public ResponseApiClientMovieById findByMovieId(Long id) {
        Params params = new Params(apiKey);
        try {
            return movieSearchProxy.getMovieById(params, id);
        } catch (FeignException.FeignClientException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}

