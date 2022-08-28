package br.com.compass.search.dto.apiTheMoviedb.searchByActor;

import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResult;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseApiResultActorKnownFor extends ResponseApiResult {
    @JsonProperty("media_type")
    private String mediaType;
}
