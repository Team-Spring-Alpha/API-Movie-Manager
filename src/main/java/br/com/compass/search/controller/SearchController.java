package br.com.compass.search.controller;


import br.com.compass.search.dto.apiclient.response.ResponseApiUserDTO;
import br.com.compass.search.dto.apiclient.response.ResponseApiUserMovieByIdDTO;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import br.com.compass.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;


    @GetMapping("/{id}/recommendations")
    public ResponseEntity<HashSet<ResponseApiUserDTO>> getRecommendations(@PathVariable Long id) {
        HashSet<ResponseApiUserDTO> responseApiUserDTOList = searchService.findMoviesRecommendations(id);
        return ResponseEntity.ok(responseApiUserDTOList);
    }

    @GetMapping("/movie-filters")
    public ResponseEntity<HashSet<ResponseApiUserDTO>> getMovieByFilters
            (@RequestParam(required = false, name = "movie_genrer") GenresEnum movieGenre,
             @RequestParam(required = false, name = "release_date_after") @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateGte,
             @RequestParam(required = false, name = "release_date_before") @Valid @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateLte,
             @RequestParam(required = false, name = "movie_provider") ProvidersEnum movieProvider,
             @RequestParam(required = false, name = "movie_peoples") List<String> moviePeoples,
             @RequestParam(required = false, name = "movie_name")String movieName) {
        HashSet<ResponseApiUserDTO> responseApiUserDTOList = searchService.findByFilters(movieGenre, dateGte, dateLte, movieProvider, moviePeoples,movieName);
        return ResponseEntity.ok(responseApiUserDTOList);
    }

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<ResponseApiUserMovieByIdDTO> getMovieById(@PathVariable Long movieId) {
        ResponseApiUserMovieByIdDTO responseMovieById = searchService.findByMovieId(movieId);
        return ResponseEntity.ok(responseMovieById);
    }
}
