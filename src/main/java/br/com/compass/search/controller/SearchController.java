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
    public ResponseEntity<List<ResponseApiClient>> getMovieByName(@RequestParam String movieName) {
        List<ResponseApiClient> responseApiClientList = searchService.findByName(movieName);
        return ResponseEntity.ok(responseApiClientList);
    }

    @GetMapping("/movie-info")
    public ResponseEntity<ResponseApiClient> getMovieInfo(@RequestParam String movieName) {
        ResponseApiClient responseApiClientList = searchService.showMovieInfo(movieName);
        return ResponseEntity.ok(responseApiClientList);
    }
    @GetMapping("/{id}/recommendations")
    public ResponseEntity<List<ResponseApiClient>> getRecommendations(@PathVariable Long id) {
        List<ResponseApiClient> responseApiClientList = searchService.findMoviesRecommendations(id);
        return ResponseEntity.ok(responseApiClientList);
    }

    @GetMapping("/movie-genre")
    public ResponseEntity<List<ResponseApiClient>> getMovieByGenre(@RequestParam GenresEnum movieGenre) {
        List<ResponseApiClient> responseApiClientList = searchService.findByGenre(movieGenre.getIdGenrer());
        return ResponseEntity.ok(responseApiClientList);
    }

    @GetMapping("/movie-date")
    public ResponseEntity<List<ResponseApiClient>> getMovieByDate
            (@RequestParam(required = false) @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateGte,
             @RequestParam(required = false) @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateLte) {
        List<ResponseApiClient> responseApiClient = searchService.findByDate(dateGte, dateLte);
        return ResponseEntity.ok(responseApiClient);
    }

    @GetMapping("/movie-actor")
    public ResponseEntity<List<ResponseApiClient>> getMovieByActor(@RequestParam String movieActor) {
        List<ResponseApiClient> responseApiClientList = searchService.findByActor(movieActor);
        return ResponseEntity.ok(responseApiClientList);
    }

    @GetMapping("/movie-provider")
    public ResponseEntity<List<ResponseApiClient>> getMovieByProvider(@RequestParam ProvidersEnum movieProvider) {
        List<ResponseApiClient> responseApiClientList = searchService.findByProvider(movieProvider);
        return ResponseEntity.ok(responseApiClientList);
    }
}
