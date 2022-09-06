package br.com.compass.search.client;

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
import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiSearchByActor;
import br.com.compass.search.dto.apiclient.response.*;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.service.RentPrice;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
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

    public HashSet<ResponseApiClient> getMovieSearchByName(ParamsSearchByName searchByName) {
        try {
            ResponseApiSearchBy movieByName = movieSearch.getMovieByName(searchByName);
            return buildResponseClientList(movieByName);
        }catch (FeignException.FeignClientException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public HashSet<ResponseApiClient> getMovieSearchByFilters(ParamsSearchByFilters searchByFilters, String releaseDateAfter, String releaseDateBefore) {
        try {
            ResponseApiSearchBy movieByFilters = movieSearch.getMovieByFilters(searchByFilters, releaseDateAfter, releaseDateBefore);
            return buildResponseClientList(movieByFilters);
        }catch (FeignException.FeignClientException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public HashSet<ResponseApiClient> getMovieByRecommendation(ParamsSearchByRecommendations byRecommendations, Long movieId) {
        try {
            ResponseApiSearchBy movieByRecommendations = movieSearch.getMovieByRecommendations(byRecommendations, movieId);
            return buildResponseClientList(movieByRecommendations);
        }catch (FeignException.FeignClientException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private ResponseJustWatch getMovieJustWatch(Long movieId, Double rentPrice, Params params) {

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

    private List<String> getMovieActors(Params params, Long movieId) {
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

    private HashSet<ResponseApiClient> buildResponseClientList(ResponseApiSearchBy apiSearchBy) {
        Params params = new Params(apiKey);
        HashSet<ResponseApiClient> responseApiClientList = new HashSet<>();
        for (int i = 0; i < apiSearchBy.getResults().size(); i++) {
            ResponseApiResult responseMovie = apiSearchBy.getResults().get(i);
            ResponseApiClient responseApiClient = new ResponseApiClient();

            List<GenresEnum> genresEnumList = genresIdToGenresString(responseMovie.getGenreIds());
            List<String> actors = getMovieActors(params, responseMovie.getId());

            String yearRelease = getYearRelease(responseMovie);

            Double rentPrice = this.rentPrice.getRentPriceFromYear(yearRelease);
            ResponseJustWatch responseJustWatch = getMovieJustWatch(responseMovie.getId(), rentPrice, params);

            responseApiClient.setMovieId(responseMovie.getId());
            responseApiClient.setTitle(responseMovie.getTitle());
            responseApiClient.setGenrers(genresEnumList);
            responseApiClient.setReleaseYear(yearRelease);
            responseApiClient.setActors(actors);
            responseApiClient.setOverview(responseMovie.getOverview());
            responseApiClient.setPoster(responseMovie.getPosterPath());
            responseApiClient.setJustWatch(responseJustWatch);

            responseApiClientList.add(responseApiClient);
        }
        return responseApiClientList;
    }

    private String getYearRelease(ResponseApiResult responseApiResult) {
        String yearRelease = "2020";
        if (responseApiResult.getReleaseDate() != null && !responseApiResult.getReleaseDate().isBlank()) {
            yearRelease = responseApiResult.getReleaseDate().substring(0, 4);
        }
        return yearRelease;
    }

    private List<GenresEnum> genresIdToGenresString(List<Long> genresIds) {
        List<GenresEnum> genresEnumList = new ArrayList<>();
        for (Long genresId : genresIds) {
            GenresEnum genresEnum = GenresEnum.valueOfId(genresId);
            genresEnumList.add(genresEnum);
        }
        return genresEnumList;
    }

    public List<Long> actorsStringToActorsId (List<String> actors) {
        List<Long> actorsId = new ArrayList<>();
        for (int i = 0; i < actors.size(); i++) {
            ResponseApiSearchByActor moviesByActors = movieSearch.getMoviesByActors(new ParamsSearchByName(apiKey, actors.get(i)));
            List<ResponseApiResultActor> results = moviesByActors.getResults();

            for (int j = 0; j < results.size(); j++){
                boolean acting = results.get(j).getKnownForDepartment().equals("Acting");
                if (acting){
                    actorsId.add(results.get(j).getId());
                }
            }
        }
        return actorsId;
    }

    public ResponseApiClientMovieById getMovieById(Params params, Long id) {
        try {
            ResponseApiResult movieById = movieSearch.getMovieById(params, id);

            ResponseApiClientMovieById responseApiClientMovieById = new ResponseApiClientMovieById();
            String yearRelease = getYearRelease(movieById);
            Double rentPrice = this.rentPrice.getRentPriceFromYear(yearRelease);

            responseApiClientMovieById.setId(movieById.getId());
            responseApiClientMovieById.setMovieName(movieById.getTitle());

            ResponseJustWatch movieJustWatch = getMovieJustWatch(movieById.getId(), rentPrice, params);
            responseApiClientMovieById.setJustWatch(movieJustWatch);

            return responseApiClientMovieById;
        }catch (FeignException.FeignClientException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
