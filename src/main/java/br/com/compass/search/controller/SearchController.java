package br.com.compass.search.controller;


import br.com.compass.search.dto.apiclient.response.ResponseApiClient;
import br.com.compass.search.dto.apiclient.response.ResponseApiClientMovieById;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import br.com.compass.search.service.SearchService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("api/movie-manager")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @ApiOperation(value = "return a movie list based on a movie id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping("/{id}/recommendations")
    public ResponseEntity<HashSet<ResponseApiClient>> getRecommendations(@PathVariable Long id) {
        HashSet<ResponseApiClient> responseApiClientList = searchService.findMoviesRecommendations(id);
        return ResponseEntity.ok(responseApiClientList);
    }

    @ApiOperation(value = "return a movie list")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK")
    })
    @GetMapping("/movie-search")
    public ResponseEntity<HashSet<ResponseApiClient>> getMovieByFilters
            (@RequestParam(required = false, name = "movie_genrer") GenresEnum movieGenre,
             @RequestParam(required = false, name = "release_date_after") @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateGte,
             @RequestParam(required = false, name = "release_date_before") @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateLte,
             @RequestParam(required = false, name = "movie_provider") ProvidersEnum movieProvider,
             @RequestParam(required = false, name = "movie_peoples") List<String> moviePeoples,
             @RequestParam(required = false, name = "movie_name")String movieName) {
        HashSet<ResponseApiClient> responseApiClientList = searchService.findByFilters(movieGenre, dateGte, dateLte, movieProvider, moviePeoples,movieName);
        return ResponseEntity.ok(responseApiClientList);
    }

    @ApiOperation(value = "return a movie info")
    @ApiResponses({
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ResponseApiClientMovieById> getMovieById(@PathVariable Long movieId) {
        ResponseApiClientMovieById responseMovieById = searchService.findByMovieId(movieId);
        return ResponseEntity.ok(responseMovieById);
    }
}
