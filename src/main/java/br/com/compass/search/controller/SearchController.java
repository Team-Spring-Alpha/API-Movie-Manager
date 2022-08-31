package br.com.compass.search.controller;


import br.com.compass.search.dto.apiclient.response.ResponseApiClient;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import br.com.compass.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/movie-name")
    public ResponseEntity<List<ResponseApiClient>> getMovieByName(@RequestParam(name = "movie_name") String movieName) {
        List<ResponseApiClient> responseApiClientList = searchService.findByName(movieName);
        return ResponseEntity.ok(responseApiClientList);
    }

    @GetMapping("/{id}/recommendations")
    public ResponseEntity<List<ResponseApiClient>> getRecommendations(@PathVariable Long id) {
        List<ResponseApiClient> responseApiClientList = searchService.findMoviesRecommendations(id);
        return ResponseEntity.ok(responseApiClientList);
    }

    @GetMapping("/movie-filters")
    public ResponseEntity<List<ResponseApiClient>> getMovieByFilters
            (@RequestParam(required = false, name = "movie_genrer") GenresEnum movieGenre,
             @RequestParam(required = false, name = "release_date_after") @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateGte,
             @RequestParam(required = false, name = "release_date_before") @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateLte,
             @RequestParam(required = false, name = "movie_provider") ProvidersEnum movieProvider,
             @RequestParam(required = false, name = "movie_peoples") List<String> moviePeoples) {
        List<ResponseApiClient> responseApiClientList = searchService.findByFilters(movieGenre, dateGte, dateLte, movieProvider, moviePeoples);
        return ResponseEntity.ok(responseApiClientList);
    }
}
