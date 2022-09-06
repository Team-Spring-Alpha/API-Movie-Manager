package br.com.compass.search.dto.apiTheMoviedb.movieProviders;

import lombok.Data;

@Data
public class ResponseApiMovieProvidersDTO {
    private Long id;
    private ResponseApiMovieProvidersResultsDTO results;
}
