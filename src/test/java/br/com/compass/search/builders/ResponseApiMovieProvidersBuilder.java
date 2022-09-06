package br.com.compass.search.builders;

import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieProvidersDTO;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieProvidersResultsDTO;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieRentBuyDTO;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseBrProvidersDTO;
import br.com.compass.search.enums.ProvidersEnum;

import java.util.ArrayList;
import java.util.List;

public class ResponseApiMovieProvidersBuilder {

    private ResponseApiMovieProvidersDTO movieProviders;

    public ResponseApiMovieProvidersBuilder() {
    }

    public static ResponseApiMovieProvidersBuilder one() {
        ResponseApiMovieProvidersBuilder builder = new ResponseApiMovieProvidersBuilder();
        builder.movieProviders = new ResponseApiMovieProvidersDTO();

        ResponseApiMovieRentBuyDTO rentMovie = new ResponseApiMovieRentBuyDTO();
        rentMovie.setProviderId(ProvidersEnum.NETFLIX.getIdProvider().intValue());
        rentMovie.setProviderName(ProvidersEnum.NETFLIX.toString());

        List<ResponseApiMovieRentBuyDTO> providerList = new ArrayList<>();
        providerList.add(rentMovie);

        ResponseBrProvidersDTO brProviders = new ResponseBrProvidersDTO();
        brProviders.setBuy(providerList);
        brProviders.setFlatrate(providerList);
        brProviders.setRent(providerList);

        ResponseApiMovieProvidersResultsDTO movieProvidersResults = new ResponseApiMovieProvidersResultsDTO();
        movieProvidersResults.setBr(brProviders);

        builder.movieProviders.setId(1L);
        builder.movieProviders.setResults(movieProvidersResults);
        return builder;
    }

    public ResponseApiMovieProvidersBuilder withBuyProviders(List<ResponseApiMovieRentBuyDTO> buyProviders) {
        this.movieProviders.getResults().getBr().setBuy(buyProviders);
        return this;
    }

    public ResponseApiMovieProvidersBuilder withRentProviders(List<ResponseApiMovieRentBuyDTO> rentProviders) {
        this.movieProviders.getResults().getBr().setRent(rentProviders);
        return this;
    }

    public ResponseApiMovieProvidersBuilder withFlatrateProviders(List<ResponseApiMovieRentBuyDTO> flatrate) {
        this.movieProviders.getResults().getBr().setFlatrate(flatrate);
        return this;
    }

    public ResponseApiMovieProvidersDTO now() {
        return movieProviders;
    }

}
