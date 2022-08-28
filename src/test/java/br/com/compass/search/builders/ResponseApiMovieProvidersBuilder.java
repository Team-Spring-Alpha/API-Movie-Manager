package br.com.compass.search.builders;

import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieProviders;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieProvidersResults;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieRentBuy;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseBrProviders;
import br.com.compass.search.enums.ProvidersEnum;

import java.util.ArrayList;
import java.util.List;

public class ResponseApiMovieProvidersBuilder {

    private ResponseApiMovieProviders movieProviders;

    public ResponseApiMovieProvidersBuilder() {
    }

    public static ResponseApiMovieProvidersBuilder one() {
        ResponseApiMovieProvidersBuilder builder = new ResponseApiMovieProvidersBuilder();
        builder.movieProviders = new ResponseApiMovieProviders();

        ResponseApiMovieRentBuy rentMovie = new ResponseApiMovieRentBuy();
        rentMovie.setProviderId(ProvidersEnum.NETFLIX.getIdProvider().intValue());
        rentMovie.setProviderName(ProvidersEnum.NETFLIX.toString());

        List<ResponseApiMovieRentBuy> providerList = new ArrayList<>();
        providerList.add(rentMovie);

        ResponseBrProviders brProviders = new ResponseBrProviders();
        brProviders.setBuy(providerList);
        brProviders.setFlatrate(providerList);
        brProviders.setRent(providerList);

        ResponseApiMovieProvidersResults movieProvidersResults = new ResponseApiMovieProvidersResults();
        movieProvidersResults.setBr(brProviders);

        builder.movieProviders.setId(1L);
        builder.movieProviders.setResults(movieProvidersResults);
        return builder;
    }

    public ResponseApiMovieProvidersBuilder withBuyProviders(List<ResponseApiMovieRentBuy> buyProviders) {
        this.movieProviders.getResults().getBr().setBuy(buyProviders);
        return this;
    }

    public ResponseApiMovieProvidersBuilder withRentProviders(List<ResponseApiMovieRentBuy> rentProviders) {
        this.movieProviders.getResults().getBr().setRent(rentProviders);
        return this;
    }

    public ResponseApiMovieProvidersBuilder withFlatrateProviders(List<ResponseApiMovieRentBuy> flatrate) {
        this.movieProviders.getResults().getBr().setFlatrate(flatrate);
        return this;
    }

    public ResponseApiMovieProviders now() {
        return movieProviders;
    }

}
