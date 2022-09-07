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

        List<ResponseApiMovieCreditsCast> castList = buildCastList(4);

        builder.movieCredits.setId(1L);
        builder.movieCredits.setCast(castList);
        builder.movieCredits.setCrew(null);

        return builder;
    }

    private static List<ResponseApiMovieCreditsCast> buildCastList(int listSize) {
        List<ResponseApiMovieCreditsCast> castList = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            ResponseApiMovieCreditsCast cast = new ResponseApiMovieCreditsCast();
            cast.setId((long) i);
            cast.setName("Test Name " + i);
            cast.setKnownForDepartment("Acting");
            castList.add(cast);
        }
        return castList;
    }

    public ResponseApiMovieCredits now() {
        return movieCredits;
    }

}
