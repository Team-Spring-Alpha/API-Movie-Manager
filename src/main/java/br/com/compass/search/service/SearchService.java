package br.com.compass.search.service;

import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchBy;
import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiSearchByActor;
import br.com.compass.search.dto.apiclient.response.ResponseApiClient;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import br.com.compass.search.proxy.MovieSearchProxy;
import br.com.compass.search.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final MovieSearchProxy movieSearchProxy;

    private final WebClient.Builder webBuider;

    private final ModelMapperUtils modelMapperUtils;

    @Value("${API_KEY}")
    private String apiKey;

    public List<ResponseApiClient> findByName(String movieName) {
        ParamsSearchByName searchByName = new ParamsSearchByName(apiKey, movieName);
        ResponseApiSearchBy responseApiSearchBy = movieSearchProxy.getMovieSearchByName(searchByName);

        return modelMapperUtils.responseSearchToApiClient(responseApiSearchBy);
    }

//    public ResponseApiClient showMovieInfo(String movieName) {
//        ResponseApiSearchBy responseApiSearchBy = webBuider.build().get().uri(uriBuilder -> uriBuilder
//                .scheme("https").host("api.themoviedb.org")
//                .path("/3/search/movie")
//                .queryParam("language", "pt-BR")
//                .queryParam("api_key", apiKey)
//                .queryParam("include_adult", false)
//                .queryParam("page", 1)
//                .queryParam("query", movieName).build()).retrieve().bodyToMono(ResponseApiSearchBy.class).block();
//
//       ResponseApiClient responseApiClient = new ResponseApiClient();
//       return responseApiClient;
//    }

    public List<ResponseApiClient> findMoviesRecommendations(Long movieId) {
        ParamsSearchByRecommendations searchByRecommendations = new ParamsSearchByRecommendations(apiKey);

        ResponseApiSearchBy responseApiSearchBy = movieSearchProxy.getMovieByRecommendation(searchByRecommendations, movieId);

        return modelMapperUtils.responseSearchToApiClient(responseApiSearchBy);
    }
    public List<ResponseApiClient> findByActor(String movieActor){
        ParamsSearchByName searchByName = new ParamsSearchByName(apiKey, movieActor);
        ResponseApiSearchByActor responseApiSearchByActor = movieSearchProxy.getMovieByActorName(searchByName);
        return modelMapperUtils.responseSearchByActorToApiClient(responseApiSearchByActor);
    }

    public List<ResponseApiClient> findByFilters(GenresEnum movieGenre, LocalDate dateGte, LocalDate dateLte, ProvidersEnum movieProvider) {
        ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(apiKey);
        if (movieGenre != null) {
            searchByFilters.setWith_genres(movieGenre.getIdGenrer());
        }
        if (movieProvider != null) {
            searchByFilters.setWith_watch_providers(movieProvider.getIdProvider());
        }

        String dateAfterString = null;
        String dateBeforeString = null;
        if (dateGte != null) {
            dateAfterString = dateGte.toString();
        }
        if (dateLte != null) {
            dateBeforeString = dateLte.toString();
        }

        ResponseApiSearchBy responseApiSearchBy = movieSearchProxy.getMovieSearchByFilters(searchByFilters, dateAfterString, dateBeforeString);
        return modelMapperUtils.responseSearchToApiClient(responseApiSearchBy);
    }
}
