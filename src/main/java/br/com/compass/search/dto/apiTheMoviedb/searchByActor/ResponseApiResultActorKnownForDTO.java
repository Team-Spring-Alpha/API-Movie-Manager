package br.com.compass.search.dto.apiTheMoviedb.searchByActor;

import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResultDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseApiResultActorKnownForDTO extends ResponseApiResultDTO {
    @JsonProperty("media_type")
    private String mediaType;
}
