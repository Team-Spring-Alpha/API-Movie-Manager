package br.com.compass.search.dto.apiTheMoviedb.movieProviders;

import lombok.Data;

@Data
public class ResponseApiMovieProviders {
    private Long id;
    private ResponseApiMovieProvidersResults results;
}
