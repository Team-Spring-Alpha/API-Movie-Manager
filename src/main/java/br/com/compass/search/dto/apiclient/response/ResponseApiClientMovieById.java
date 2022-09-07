package br.com.compass.search.dto.apiclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseApiClientMovieById {
    private Long id;
    @JsonProperty("movie_name")
    private String movieName;
    @JsonProperty("just_watch")
    private ResponseJustWatch justWatch;
}
