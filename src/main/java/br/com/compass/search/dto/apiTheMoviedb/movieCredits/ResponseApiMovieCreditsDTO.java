package br.com.compass.search.dto.apiTheMoviedb.movieCredits;

import lombok.Data;

import java.util.List;

@Data
public class ResponseApiMovieCreditsDTO {
    private Long id;
    private List<ResponseApiMovieCreditsCastDTO> cast;
    private List<ResponseApiMovieCreditsCrewDTO> crew;
}
