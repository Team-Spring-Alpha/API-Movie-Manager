package br.com.compass.search.builders;

import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCreditsDTO;
import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCreditsCastDTO;

import java.util.ArrayList;
import java.util.List;

public class ResponseApiMovieCreditsBuilder {

    private ResponseApiMovieCreditsDTO movieCredits;

    public ResponseApiMovieCreditsBuilder() {
    }

    public static ResponseApiMovieCreditsBuilder one() {
        ResponseApiMovieCreditsBuilder builder = new ResponseApiMovieCreditsBuilder();
        builder.movieCredits = new ResponseApiMovieCreditsDTO();

        ResponseApiMovieCreditsCastDTO cast = new ResponseApiMovieCreditsCastDTO();
        cast.setId(1L);
        cast.setName("Test Name");
        cast.setKnownForDepartment("Acting");

        ResponseApiMovieCreditsCastDTO castTwo = new ResponseApiMovieCreditsCastDTO();
        castTwo.setId(2L);
        castTwo.setName("Test Name 2");
        castTwo.setKnownForDepartment("Acting");

        List<ResponseApiMovieCreditsCastDTO> castList = new ArrayList<>();
        castList.add(cast);
        castList.add(castTwo);

        builder.movieCredits.setId(1L);
        builder.movieCredits.setCast(castList);
        builder.movieCredits.setCrew(null);

        return builder;
    }

    public ResponseApiMovieCreditsDTO now() {
        return movieCredits;
    }

}
