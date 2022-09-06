package br.com.compass.search.dto.apiclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseApiUserMovieByIdDTO {
    private Long id;
    @JsonProperty("movie_name")
    private String movieName;
    @JsonProperty("just_watch")
    private ResponseJustWatchDTO justWatch;
}
