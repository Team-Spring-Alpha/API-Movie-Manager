package br.com.compass.search.builders;

import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResult;
import br.com.compass.search.enums.GenresEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ResponseApiResultBuilder {

    private ResponseApiResult result;

    public ResponseApiResultBuilder() {
    }

    public static ResponseApiResultBuilder one() {
        ResponseApiResultBuilder builder = new ResponseApiResultBuilder();
        builder.result = new ResponseApiResult();

        List<Long> genreIdsList = new ArrayList<>();
        genreIdsList.add(GenresEnum.ACAO.getIdGenrer());
        genreIdsList.add(GenresEnum.ANIMACAO.getIdGenrer());

        builder.result.setId(1L);
        builder.result.setTitle("test title");
        builder.result.setOverview("test overview");
        builder.result.setAdult(false);
        builder.result.setBackdropPath("backdrop path test");
        builder.result.setGenreIds(genreIdsList);
        builder.result.setOriginalLanguage("pt-BR");
        builder.result.setOriginalTitle("titulo teste");
        builder.result.setPopularity(100.0);
        builder.result.setReleaseDate(LocalDate.now().toString());
        builder.result.setVideo(false);
        builder.result.setVoteAverage(100.0);
        builder.result.setVoteCount(1);
        builder.result.setPosterPath("poster path test");

        return builder;
    }

    public ResponseApiResultBuilder withId(Long movieId) {
        this.result.setId(movieId);
        return this;
    }

    public ResponseApiResultBuilder withTitle(String movieTitle) {
        this.result.setTitle(movieTitle);
        return this;
    }

    public ResponseApiResultBuilder withReleaseDate(String movieReleaseDate) {
        this.result.setReleaseDate(movieReleaseDate);
        return this;
    }

    public ResponseApiResult now() {
        return this.result;
    }

}
