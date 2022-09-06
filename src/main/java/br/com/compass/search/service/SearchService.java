package br.com.compass.search.service;

import br.com.compass.search.user.MovieSearchProxy;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.Params;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiclient.response.ResponseApiUserDTO;
import br.com.compass.search.dto.apiclient.response.ResponseApiUserMovieByIdDTO;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final MovieSearchProxy movieSearchProxy;

    @Value("${API_KEY}")
    private String apiKey;


    public HashSet<ResponseApiUserDTO> findMoviesRecommendations(Long movieId) {
        ParamsSearchByRecommendations searchByRecommendations = new ParamsSearchByRecommendations(apiKey);
        return movieSearchProxy.getMovieByRecommendation(searchByRecommendations, movieId);
    }

    public HashSet<ResponseApiUserDTO> findByFilters(GenresEnum movieGenre, LocalDate dateGte, LocalDate dateLte,
                                                     ProvidersEnum movieProvider, List<String> moviePeoples, String movieName) {
        HashSet<ResponseApiUserDTO> movieSearchByName = new HashSet<>();
        HashSet<ResponseApiUserDTO> movieSearchByFilters = new HashSet<>();

        if (movieName != null) {
            ParamsSearchByName searchByName = new ParamsSearchByName(apiKey, movieName);
            movieSearchByName = movieSearchProxy.getMovieSearchByName(searchByName);
        }

        if (movieGenre != null || dateGte != null || dateLte != null || movieProvider != null || movieName == null || moviePeoples != null) {
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

        if (!movieSearchByName.isEmpty()) {
            movieSearchByFilters.addAll(movieSearchByName);
        }

        return movieSearchByFilters;

    }

    public ResponseApiUserMovieByIdDTO findByMovieId(Long id) {
        Params params = new Params(apiKey);
        return movieSearchProxy.getMovieById(params, id);
    }
}

