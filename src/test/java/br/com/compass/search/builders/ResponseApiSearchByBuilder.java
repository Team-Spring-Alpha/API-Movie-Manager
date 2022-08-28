package br.com.compass.search.builders;

import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResult;
import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchBy;

import java.util.ArrayList;
import java.util.List;

public class ResponseApiSearchByBuilder {

    private ResponseApiSearchBy searchBy;

    public ResponseApiSearchByBuilder() {
    }

    public static ResponseApiSearchByBuilder one() {
        ResponseApiSearchByBuilder builder = new ResponseApiSearchByBuilder();
        builder.searchBy = new ResponseApiSearchBy();

        ResponseApiResult movieOne = ResponseApiResultBuilder.one().now();
        ResponseApiResult movieTwo = ResponseApiResultBuilder.one().withId(2L).withTitle("movie test 2").now();

        List<ResponseApiResult> moviesList = new ArrayList<>();
        moviesList.add(movieOne);
        moviesList.add(movieTwo);

        builder.searchBy.setPage(1);
        builder.searchBy.setTotalPages(1);
        builder.searchBy.setTotalResults(1);
        builder.searchBy.setResults(moviesList);
        return builder;
    }

    public ResponseApiSearchByBuilder withResults(List<ResponseApiResult> movieList) {
        this.searchBy.setResults(movieList);
        return this;
    }

    public ResponseApiSearchBy now() {
        return this.searchBy;
    }

}
