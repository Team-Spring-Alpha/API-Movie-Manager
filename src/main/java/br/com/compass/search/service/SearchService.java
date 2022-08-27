package br.com.compass.search.service;

import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchBy;
import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiSearchByActor;
import br.com.compass.search.dto.apiclient.response.ResponseApiClient;
import br.com.compass.search.enums.ProvidersEnum;
import br.com.compass.search.proxy.MovieSearchProxy;
import br.com.compass.search.utils.ModelMapperUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public List<ResponseApiClient> findByGenre(Long movieGenre) {
        ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(apiKey);
        searchByFilters.setWith_genres(movieGenre);

        ResponseApiSearchBy responseApiSearchBy = movieSearchProxy.getMovieSearchByFilters(searchByFilters);
        return modelMapperUtils.responseSearchToApiClient(responseApiSearchBy);
    }

    public List<ResponseApiClient> findByDate(LocalDate dateGte, LocalDate dateLte) {
        ResponseApiSearchBy responseApiSearchBy = webBuider.build().get().uri(uriBuilder -> uriBuilder
                        .scheme("https").host("api.themoviedb.org")
                        .path("/3/discover/movie")
                        .queryParam("api_key", apiKey)
                        .queryParam("language", "pt-BR")
                        .queryParam("include_adult", false)
                        .queryParam("page", 1)
                        .queryParamIfPresent("primary_release_date.gte", Optional.ofNullable(dateGte))
                        .queryParamIfPresent("primary_release_date.lte", Optional.ofNullable(dateLte))
                        .build())
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        error -> Mono.error(new RuntimeException("Verifique os parâmetros")))
                .bodyToMono(ResponseApiSearchBy.class)
                .block();
        return modelMapperUtils.responseSearchToApiClient(responseApiSearchBy);
    }


    public List<ResponseApiClient> findByProvider(ProvidersEnum movieProvider) {
        ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(apiKey);
        searchByFilters.setWith_watch_providers(movieProvider.getIdProvider());

        ResponseApiSearchBy responseApiSearchBy = movieSearchProxy.getMovieSearchByFilters(searchByFilters);
        return modelMapperUtils.responseSearchToApiClient(responseApiSearchBy);
    }
    public List<ResponseApiClient> findByActor(String movieActor){
        ParamsSearchByName searchByName = new ParamsSearchByName(apiKey, movieActor);
        ResponseApiSearchByActor responseApiSearchByActor = movieSearchProxy.getMovieByActorName(searchByName);
        return modelMapperUtils.responseSearchByActorToApiClient(responseApiSearchByActor);
    }
}
