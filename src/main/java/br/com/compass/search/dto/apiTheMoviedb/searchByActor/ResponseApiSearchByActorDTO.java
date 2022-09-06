package br.com.compass.search.dto.apiTheMoviedb.searchByActor;

import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResultActorDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseApiSearchByActorDTO {
    private int page;
    private List<ResponseApiResultActorDTO> results;
    @JsonProperty("total_results")
    private int totalResults;
    @JsonProperty("total_pages")
    private int totalPages;
}
