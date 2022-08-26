package br.com.compass.search.dto.apiTheMoviedb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseApiResult {
    @JsonProperty("poster_path")
    private String posterPath;
    private boolean adult;
    private String overview;
    @JsonProperty("release_date")
    private String releaseDate;
    @JsonProperty("genre_ids")
    private List<Long> genreIds;
    private Long id;
    @JsonProperty("original_title")
    private String originalTitle;
    @JsonProperty("original_language")
    private String originalLanguage;
    private String title;
    @JsonProperty("backdrop_path")
    private String backdropPath;
    private Double popularity;
    @JsonProperty("vote_count")
    private int voteCount;
    private boolean video;
    @JsonProperty("vote_average")
    private Double voteAverage;
}
