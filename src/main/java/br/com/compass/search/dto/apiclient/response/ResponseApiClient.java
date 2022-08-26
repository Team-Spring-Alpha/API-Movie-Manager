package br.com.compass.search.dto.apiclient.response;

import br.com.compass.search.enums.GenresEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseApiClient {
    @JsonProperty("movie_id")
    private Long movieId;
    private String title;
    private List<GenresEnum> genrers;
    @JsonProperty("release_year")
    private String releaseYear;
    private List<String> actors;
    private String overview;
    private String poster;
    @JsonProperty("just_watch")
    private ResponseJustWatch justWatch;
}
