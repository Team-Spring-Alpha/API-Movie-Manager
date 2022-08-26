package br.com.compass.search.dto.apiTheMoviedb.movieProviders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseApiMovieProvidersResults {
    @JsonProperty("BR")
    private ResponseBrProviders br;
}
