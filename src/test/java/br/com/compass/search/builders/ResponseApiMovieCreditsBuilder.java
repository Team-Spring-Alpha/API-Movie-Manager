package br.com.compass.search.builders;

import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCredits;
import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCreditsCast;

import java.util.ArrayList;
import java.util.List;

public class ResponseApiMovieCreditsBuilder {

    private ResponseApiMovieCredits movieCredits;

    public ResponseApiMovieCreditsBuilder() {
    }

    public static ResponseApiMovieCreditsBuilder one() {
        ResponseApiMovieCreditsBuilder builder = new ResponseApiMovieCreditsBuilder();
        builder.movieCredits = new ResponseApiMovieCredits();

        ResponseApiMovieCreditsCast cast = new ResponseApiMovieCreditsCast();
        cast.setId(1L);
        cast.setName("Test Name");
        cast.setKnownForDepartment("Acting");

        ResponseApiMovieCreditsCast castTwo = new ResponseApiMovieCreditsCast();
        castTwo.setId(2L);
        castTwo.setName("Test Name 2");
        castTwo.setKnownForDepartment("Acting");

        List<ResponseApiMovieCreditsCast> castList = new ArrayList<>();
        castList.add(cast);
        castList.add(castTwo);

        builder.movieCredits.setId(1L);
        builder.movieCredits.setCast(castList);
        builder.movieCredits.setCrew(null);

        return builder;
    }

    public ResponseApiMovieCredits now() {
        return movieCredits;
    }

}
