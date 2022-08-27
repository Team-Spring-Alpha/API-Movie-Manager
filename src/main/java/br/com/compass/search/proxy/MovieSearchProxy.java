package br.com.compass.search.proxy;

import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCredits;
import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCreditsCast;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.Params;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieProviders;
import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchBy;
import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiSearchByActor;
import br.com.compass.search.dto.apiclient.response.ResponseFlatrate;
import br.com.compass.search.dto.apiclient.response.ResponseJustWatch;
import br.com.compass.search.dto.apiclient.response.ResponseRentAndBuy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MovieSearchProxy {

    @Autowired
    private MovieSearch movieSearch;

    public ResponseApiSearchBy getMovieSearchByName(ParamsSearchByName searchByName) {
        return movieSearch.getMovieByName(searchByName);
    }

    public ResponseApiSearchBy getMovieSearchByFilters(ParamsSearchByFilters searchByFilters, String releaseDateAfter, String releaseDateBefore) {
        return movieSearch.getMovieByFilters(searchByFilters, releaseDateAfter, releaseDateBefore);
    }

    public ResponseApiSearchBy getMovieByRecommendation(ParamsSearchByRecommendations byRecommendations, Long movieId) {
        return movieSearch.getMovieByRecommendations(byRecommendations, movieId);
    }

    public ResponseApiSearchByActor getMovieByActorName(ParamsSearchByName searchByName) {
        return movieSearch.getMoviesByActors(searchByName);
    }

    public ResponseJustWatch getMovieJustWatch(Long movieId, Double rentPrice, Params params) {

        ResponseApiMovieProviders moviesWatchProviders = movieSearch.getMovieWatchProviders(params, movieId);

        if (moviesWatchProviders.getResults().getBr() == null) {
            return null;
        }

        ResponseJustWatch responseJustWatch = new ResponseJustWatch();

        if (moviesWatchProviders.getResults().getBr().getBuy() != null) {
            List<ResponseRentAndBuy> responseBuyList = new ArrayList<>();
            for (int i = 0; i < moviesWatchProviders.getResults().getBr().getBuy().size(); i++) {
                ResponseRentAndBuy buy = new ResponseRentAndBuy();
                buy.setStore(moviesWatchProviders.getResults().getBr().getBuy().get(i).getProviderName());
                buy.setPrice(rentPrice * 1.5);
                responseBuyList.add(buy);
            }
            responseJustWatch.setBuy(responseBuyList);
        }

        if (moviesWatchProviders.getResults().getBr().getRent() != null) {
            List<ResponseRentAndBuy> responseRentList = new ArrayList<>();
            for (int i = 0; i < moviesWatchProviders.getResults().getBr().getRent().size(); i++) {
                ResponseRentAndBuy rent = new ResponseRentAndBuy();
                rent.setStore(moviesWatchProviders.getResults().getBr().getRent().get(i).getProviderName());
                rent.setPrice(rentPrice);
                responseRentList.add(rent);
            }
            responseJustWatch.setRent(responseRentList);
        }

        if (moviesWatchProviders.getResults().getBr().getFlatrate() != null) {
            List<ResponseFlatrate> responseFlatrateList = new ArrayList<>();
            for (int i = 0; i < moviesWatchProviders.getResults().getBr().getFlatrate().size(); i++) {
                ResponseFlatrate responseFlatrate = new ResponseFlatrate();
                responseFlatrate.setProviderName(moviesWatchProviders.getResults().getBr().getFlatrate().get(i).getProviderName());
                responseFlatrateList.add(responseFlatrate);
            }
            responseJustWatch.setFlatrate(responseFlatrateList);
        }

        return responseJustWatch;
    }

    public List<String> getMovieActors(Params params, Long movieId) {
        List<String> actorsList = new ArrayList<>();
        int actorListSize = 3;

        ResponseApiMovieCredits movieCredits = movieSearch.getMovieCredits(params, movieId);


        for (int i = 0; i < Objects.requireNonNull(movieCredits).getCast().size(); i++) {
            ResponseApiMovieCreditsCast cast = movieCredits.getCast().get(i);

            if (cast.getKnownForDepartment().equals("Acting")) {
                actorsList.add(cast.getName());
                actorListSize--;
            }

            if (actorListSize == 0) {
                break;
            }
        }
        return actorsList;
    }
}
