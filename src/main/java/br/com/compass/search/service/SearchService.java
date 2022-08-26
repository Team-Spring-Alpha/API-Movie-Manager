package br.com.compass.search.service;

import br.com.compass.search.dto.apiclient.response.ResponseApiClient;
import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiSearchByActor;
import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchBy;
import br.com.compass.search.enums.ProvidersEnum;
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

    private final WebClient.Builder webBuider;

    private final ModelMapperUtils modelMapperUtils;

    @Value("${API_KEY}")
    private String apiKey;

    public List<ResponseApiClient> findByName(String movieName) {
        ResponseApiSearchBy responseApiSearchBy = webBuider.build().get().uri(uriBuilder -> uriBuilder
                .scheme("https").host("api.themoviedb.org")
                .path("/3/search/movie")
                .queryParam("language", "pt-BR")
                .queryParam("api_key", apiKey)
                .queryParam("include_adult", false)
                .queryParam("page", 1)
                .queryParam("query", movieName).build()).retrieve().bodyToMono(ResponseApiSearchBy.class).block();

        return modelMapperUtils.responseSearchToApiClient(responseApiSearchBy);
    }

    public ResponseApiClient showMovieInfo(String movieName) {
        ResponseApiSearchBy responseApiSearchBy = webBuider.build().get().uri(uriBuilder -> uriBuilder
                .scheme("https").host("api.themoviedb.org")
                .path("/3/search/movie")
                .queryParam("language", "pt-BR")
                .queryParam("api_key", apiKey)
                .queryParam("include_adult", false)
                .queryParam("page", 1)
                .queryParam("query", movieName).build()).retrieve().bodyToMono(ResponseApiSearchBy.class).block();

       ResponseApiClient responseApiClient = new ResponseApiClient();
       return responseApiClient;
    }

    public List<ResponseApiClient> findMoviesRecommendations(Long movieId) {
        ResponseApiSearchBy responseApiSearchBy = webBuider.build()
                .get().uri(uriBuilder -> uriBuilder
                .scheme("https").host("api.themoviedb.org")
                .path("/3/movie/" + movieId + "/recommendations")
                .queryParam("api_key", apiKey)
                .queryParam("language", "pt-BR")
                .queryParam("page", 1)
                .build()).retrieve()
                .bodyToMono(ResponseApiSearchBy.class)
                .block();

        return modelMapperUtils.responseSearchToApiClient(responseApiSearchBy);
    }

    public List<ResponseApiClient> findByGenre(Long movieGenre) {
        ResponseApiSearchBy responseApiSearchBy = webBuider.build().get().uri(uriBuilder -> uriBuilder
                .scheme("https").host("api.themoviedb.org")
                .path("/3/discover/movie")
                .queryParam("language", "pt-BR")
                .queryParam("api_key", apiKey)
                .queryParam("include_adult", false)
                .queryParam("page", 1)
                .queryParam("with_genres", movieGenre)
                .build()).retrieve().bodyToMono(ResponseApiSearchBy.class).block();

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

    public List<ResponseApiClient> findByActor(String movieActor){
        ResponseApiSearchByActor responseApiSearchByActor = webBuider.build().get().uri(uriBuilder -> uriBuilder
                .scheme("https").host("api.themoviedb.org")
                .path("/3/search/person")
                .queryParam("language", "pt-BR")
                .queryParam("api_key", apiKey)
                .queryParam("include_adult", false)
                .queryParam("page", 1)
                .queryParam("query", movieActor).build()).retrieve().bodyToMono(ResponseApiSearchByActor.class).block();

        return modelMapperUtils.responseSearchByActorToApiClient(responseApiSearchByActor);
    }

    public List<ResponseApiClient> findByProvider(ProvidersEnum movieProvider) {
        ResponseApiSearchBy responseApiSearchBy = webBuider.build().get().uri(uriBuilder -> uriBuilder
                .scheme("https").host("api.themoviedb.org")
                .path("/3/discover/movie")
                .queryParam("language", "pt-BR")
                .queryParam("api_key", apiKey)
                .queryParam("include_adult", false)
                .queryParam("page", 1)
                .queryParam("watch_region", "BR")
                .queryParam("with_watch_providers", movieProvider.getIdProvider())
                .build()).retrieve().bodyToMono(ResponseApiSearchBy.class).block();

        return modelMapperUtils.responseSearchToApiClient(responseApiSearchBy);
    }
}
