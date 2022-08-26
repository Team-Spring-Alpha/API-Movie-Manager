package br.com.compass.search.dto.apiTheMoviedb;

import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiResultActorKnownFor;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseApiResultActor {
    private boolean adult;
    private int gender;
    private Long id;
    @JsonProperty("known_for")
    private List<ResponseApiResultActorKnownFor> results;
    @JsonProperty("known_for_department")
    private String knownForDepartment;
    private String name;
    private Double popularity;
    @JsonProperty("profile_path")
    private String profilePath;
}
