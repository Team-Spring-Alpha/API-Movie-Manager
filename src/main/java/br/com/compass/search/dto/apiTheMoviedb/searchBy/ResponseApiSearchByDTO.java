package br.com.compass.search.dto.apiTheMoviedb.searchBy;

import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResultDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseApiSearchByDTO {
    private int page;
    private List<ResponseApiResultDTO> results;
    @JsonProperty("total_results")
    private int totalResults;
    @JsonProperty("total_pages")
    private int totalPages;

}
