package br.com.compass.search.service;

import br.com.compass.search.client.MovieSearchProxy;
import br.com.compass.search.dto.apiclient.response.ResponseApiClient;
import br.com.compass.search.dto.apiclient.response.ResponseApiClientMovieById;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final MovieSearchProxy movieSearchProxy;

    public HashSet<ResponseApiClient> findMoviesRecommendations(Long movieId) {
        return movieSearchProxy.getMovieByRecommendation(movieId);
    }

    public HashSet<ResponseApiClient> findByFilters(GenresEnum movieGenre, LocalDate dateGte, LocalDate dateLte,
                                                    ProvidersEnum movieProvider, List<String> moviePeoples, String movieName) {

        boolean isMovieNameTheOnlyParam = verifyParams(movieGenre, dateGte, dateLte, movieProvider, moviePeoples, movieName);

        if (isMovieNameTheOnlyParam) {
            return movieSearchProxy.getMovieSearchByName(movieName);
        }

        Long genreId = getGenreId(movieGenre);
        Long movieProviderId = getMovieProviderId(movieProvider);
        List<Long> movieActorsIdList = getMovieActorsIdList(moviePeoples);
        String dateAfterString = getDateAfter(dateGte);
        String dateBeforeString = getDateBefore(dateLte);

        HashSet<ResponseApiClient> movieSearchByFilters = movieSearchProxy.getMovieSearchByFilters(genreId, movieProviderId, movieActorsIdList, dateAfterString, dateBeforeString);

        if (Objects.nonNull(movieName)) {
            HashSet<ResponseApiClient> movieSearchByName = movieSearchProxy.getMovieSearchByName(movieName);
            movieSearchByFilters.addAll(movieSearchByName);
        }

        return movieSearchByFilters;
    }

    public ResponseApiClientMovieById findByMovieId(Long id) {
        return movieSearchProxy.getMovieById(id);
    }

    private boolean verifyParams(GenresEnum movieGenre, LocalDate dateGte, LocalDate dateLte, ProvidersEnum movieProvider, List<String> moviePeoples, String movieName) {
        return Objects.nonNull(movieName) && Objects.isNull(movieGenre)
                && Objects.isNull(dateGte) && Objects.isNull(dateLte)
                && Objects.isNull(movieProvider) && Objects.isNull(moviePeoples);
    }

    private Long getGenreId(GenresEnum movieGenre) {
        if (Objects.nonNull(movieGenre)) {
            return movieGenre.getIdGenrer();
        }
        return null;
    }

    private Long getMovieProviderId(ProvidersEnum movieProvider) {
        if (Objects.nonNull(movieProvider)) {
            return movieProvider.getIdProvider();
        }
        return null;
    }

    private List<Long> getMovieActorsIdList(List<String> moviePeoples) {
        if (Objects.nonNull(moviePeoples)){
            return movieSearchProxy.actorsStringToActorsId(moviePeoples);
        }
        return null;
    }

    private String getDateAfter(LocalDate dateGte) {
        if (Objects.nonNull(dateGte)) {
            return dateGte.toString();
        }
        return null;
    }

    private String getDateBefore(LocalDate dateLte) {
        if (Objects.nonNull(dateLte)) {
            return dateLte.toString();
        }
        return null;
    }
}

