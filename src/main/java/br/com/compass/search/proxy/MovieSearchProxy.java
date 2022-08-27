package br.com.compass.search.proxy;

import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResult;
import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResultActor;
import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCredits;
import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCreditsCast;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.Params;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieProviders;
import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchBy;
import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiResultActorKnownFor;
import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiSearchByActor;
import br.com.compass.search.dto.apiclient.response.ResponseApiClient;
import br.com.compass.search.dto.apiclient.response.ResponseFlatrate;
import br.com.compass.search.dto.apiclient.response.ResponseJustWatch;
import br.com.compass.search.dto.apiclient.response.ResponseRentAndBuy;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.utils.RentPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MovieSearchProxy {

    @Autowired
    private MovieSearch movieSearch;

    @Value("${API_KEY}")
    private String apiKey;
    private final RentPrice rentPrice;

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

    public List<ResponseApiClient> responseSearchToApiClient(ResponseApiSearchBy apiSearchByName) {
        List<ResponseApiClient> responseApiClientList = new ArrayList<>();
        Params params = new Params(apiKey);

        for (int i = 0; i < apiSearchByName.getResults().size(); i++) {
            ResponseApiResult responseApiResult = apiSearchByName.getResults().get(i);
            ResponseApiClient responseApiClient = new ResponseApiClient();

            List<GenresEnum> genresEnumList = genresIdToGenresString(responseApiResult.getGenreIds());
            List<String> actors = getMovieActors(params, responseApiResult.getId());

            String yearRelease = "2020";
            if (!responseApiResult.getReleaseDate().isBlank()) {
                yearRelease = responseApiResult.getReleaseDate().substring(0, 4);
            }

            Double rentPrice = this.rentPrice.getRentPriceFromYear(yearRelease);
            ResponseJustWatch responseJustWatch = getMovieJustWatch(responseApiResult.getId(), rentPrice, params);

            responseApiClient.setMovieId(responseApiResult.getId());
            responseApiClient.setTitle(responseApiResult.getTitle());
            responseApiClient.setGenrers(genresEnumList);
            responseApiClient.setReleaseYear(yearRelease);
            responseApiClient.setActors(actors);
            responseApiClient.setOverview(responseApiResult.getOverview());
            responseApiClient.setPoster(responseApiResult.getPosterPath());
            responseApiClient.setJustWatch(responseJustWatch);

            responseApiClientList.add(responseApiClient);
        }
        return responseApiClientList;
    }

    public List<GenresEnum> genresIdToGenresString(List<Long> genresIds) {
        List<GenresEnum> genresEnumList = new ArrayList<>();
        for (Long genresId : genresIds) {
            GenresEnum genresEnum = GenresEnum.valueOfId(genresId);
            genresEnumList.add(genresEnum);
        }
        return genresEnumList;
    }

    public List<ResponseApiClient> responseSearchByActorToApiClient(ResponseApiSearchByActor apiSearchByActor) {
        List<ResponseApiClient> responseApiClientList = new ArrayList<>();
        Params params = new Params(apiKey);

        for (int i = 0; i < apiSearchByActor.getResults().size(); i++) {
            ResponseApiResultActor responseApiResultActor = apiSearchByActor.getResults().get(i);
            List<ResponseApiResultActorKnownFor> results = responseApiResultActor.getResults();

            for (ResponseApiResultActorKnownFor responseApiResult : results) {
                if (responseApiResult.getMediaType().equals("movie")){
                    ResponseApiClient responseApiClient = new ResponseApiClient();
                    List<GenresEnum> genresEnumList = genresIdToGenresString(responseApiResult.getGenreIds());

                    List<String> actors = getMovieActors(params, responseApiResult.getId());
                    String yearRelease = responseApiResult.getReleaseDate().substring(0, 4);
                    Double rentPrice = this.rentPrice.getRentPriceFromYear(yearRelease);
                    ResponseJustWatch responseJustWatch = getMovieJustWatch(responseApiResult.getId(), rentPrice, params);

                    responseApiClient.setMovieId(responseApiResult.getId());
                    responseApiClient.setTitle(responseApiResult.getTitle());
                    responseApiClient.setGenrers(genresEnumList);
                    responseApiClient.setReleaseYear(yearRelease);
                    responseApiClient.setActors(actors);
                    responseApiClient.setOverview(responseApiResult.getOverview());
                    responseApiClient.setPoster(responseApiResult.getPosterPath());
                    responseApiClient.setJustWatch(responseJustWatch);

                    responseApiClientList.add(responseApiClient);
                }
            }
        }
        return responseApiClientList;
    }
}
