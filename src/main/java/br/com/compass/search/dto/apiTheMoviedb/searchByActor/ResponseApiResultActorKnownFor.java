package br.com.compass.search.dto.apiTheMoviedb.searchByActor;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseApiResultActorKnownFor {
    @JsonProperty("poster_path")
    private String posterPath;
    private boolean adult;
    private String overview;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("genre_ids")
    private List<Long> genreIds;
    private Long id;
    @JsonProperty("media_type")
    private String mediaType;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("original_language")
    private String originalLanguage;
    private String title;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    @JsonProperty("vote_count")
    private int voteCount;
    private boolean video;
    @JsonProperty("vote_average")
    private Double voteAverage;
}
