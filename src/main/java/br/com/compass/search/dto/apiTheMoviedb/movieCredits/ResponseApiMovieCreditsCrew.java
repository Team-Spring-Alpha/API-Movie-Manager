package br.com.compass.search.dto.apiTheMoviedb.movieCredits;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
class ResponseApiMovieCreditsCrew {
    private boolean adult;
    private Integer gender;
    private Long id;
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    private String name;
    @JsonProperty("original_name")
    private String originalName;
    private Double popularity;
    @JsonProperty("profile_path")
    private String profilePath;
    @JsonProperty("credit_id")
    private String creditId;
    private String department;
    private String job;

}
