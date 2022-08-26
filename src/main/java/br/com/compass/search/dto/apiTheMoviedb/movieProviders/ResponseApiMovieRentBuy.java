package br.com.compass.search.dto.apiTheMoviedb.movieProviders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseApiMovieRentBuy {
    @JsonProperty("display_priority")
    private Integer displayPriority;
    @JsonProperty("logo_path")
    private String logoPath;
    @JsonProperty("provider_id")
    private Integer providerId;
    @JsonProperty("provider_name")
    private String providerName;
}
