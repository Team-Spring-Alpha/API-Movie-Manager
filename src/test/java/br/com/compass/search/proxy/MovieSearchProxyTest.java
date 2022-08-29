package br.com.compass.search.proxy;

import br.com.compass.search.builders.ResponseApiMovieCreditsBuilder;
import br.com.compass.search.builders.ResponseApiMovieProvidersBuilder;
import br.com.compass.search.builders.ResponseApiSearchByBuilder;
import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCredits;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieProviders;
import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchBy;
import br.com.compass.search.dto.apiclient.response.ResponseApiClient;
import br.com.compass.search.dto.apiclient.response.ResponseFlatrate;
import br.com.compass.search.dto.apiclient.response.ResponseRentAndBuy;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import br.com.compass.search.utils.RentPrice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = MovieSearchProxy.class)
class MovieSearchProxyTest {

    @Autowired
    private MovieSearchProxy movieSearchProxy;

    @MockBean
    private MovieSearch movieSearch;

    @MockBean
    private RentPrice rentPrice;

    @Test
    @DisplayName("should get response api client correct")
    void getMovieSearchByFilters() {
        ResponseApiSearchBy movieList = ResponseApiSearchByBuilder.one().now();
        ResponseApiMovieCredits movieCredits = ResponseApiMovieCreditsBuilder.one().now();
        ResponseApiMovieProviders movieProviders = ResponseApiMovieProvidersBuilder.one().now();

        Mockito.when(movieSearch.getMovieByFilters(null, null, null)).thenReturn(movieList);
        Mockito.when(movieSearch.getMovieCredits(any(), any())).thenReturn(movieCredits);
        Mockito.when(rentPrice.getRentPriceFromYear(any())).thenReturn(5.0);
        Mockito.when(movieSearch.getMovieWatchProviders(any(), any())).thenReturn(movieProviders);

        List<ResponseApiClient> movieResponseList = movieSearchProxy.getMovieSearchByFilters(null, null, null);

        List<String> movieActorsExpected = getMovieActorsExpected(movieCredits);
        List<GenresEnum> movieGenreEnumExpected = getMovieGenreEnumExpected();
        List<ResponseRentAndBuy> buyListExpected = getBuyListExpected(7.5);
        List<ResponseRentAndBuy> rentListExpected = getBuyListExpected(5.0);
        List<ResponseFlatrate> flatrateListExpected = getFlatrateListExpected();

        Assertions.assertNotNull(movieResponseList);
        for (int i = 0; i < movieResponseList.size(); i++) {
            Assertions.assertEquals(movieList.getResults().get(i).getTitle(), movieResponseList.get(i).getTitle());
            Assertions.assertEquals(movieList.getResults().get(i).getId(), movieResponseList.get(i).getMovieId());
            Assertions.assertEquals(movieList.getResults().get(i).getOverview(), movieResponseList.get(i).getOverview());
            Assertions.assertEquals(movieList.getResults().get(i).getReleaseDate().substring(0, 4), movieResponseList.get(i).getReleaseYear());
            Assertions.assertEquals(movieList.getResults().get(i).getPosterPath(), movieResponseList.get(i).getPoster());
            Assertions.assertEquals(movieActorsExpected, movieResponseList.get(i).getActors());
            Assertions.assertEquals(movieGenreEnumExpected, movieResponseList.get(i).getGenrers());
            Assertions.assertEquals(buyListExpected, movieResponseList.get(i).getJustWatch().getBuy());
            Assertions.assertEquals(rentListExpected, movieResponseList.get(i).getJustWatch().getRent());
            Assertions.assertEquals(flatrateListExpected, movieResponseList.get(i).getJustWatch().getFlatrate());
        }
    }

    @Test
    @DisplayName("should get correct client api response for search by name")
    void getMovieSearchByName() {
        ResponseApiSearchBy movieList = ResponseApiSearchByBuilder.one().now();
        ResponseApiMovieCredits movieCredits = ResponseApiMovieCreditsBuilder.one().now();
        ResponseApiMovieProviders movieProviders = ResponseApiMovieProvidersBuilder.one().now();

        Mockito.when(movieSearch.getMovieByName(null)).thenReturn(movieList);
        Mockito.when(movieSearch.getMovieCredits(any(), any())).thenReturn(movieCredits);
        Mockito.when(rentPrice.getRentPriceFromYear(any())).thenReturn(5.0);
        Mockito.when(movieSearch.getMovieWatchProviders(any(), any())).thenReturn(movieProviders);

        List<ResponseApiClient> movieResponseList = movieSearchProxy.getMovieSearchByName(null);

        List<String> movieActorsExpected = getMovieActorsExpected(movieCredits);
        List<GenresEnum> movieGenreEnumExpected = getMovieGenreEnumExpected();
        List<ResponseRentAndBuy> buyListExpected = getBuyListExpected(7.5);
        List<ResponseRentAndBuy> rentListExpected = getBuyListExpected(5.0);
        List<ResponseFlatrate> flatrateListExpected = getFlatrateListExpected();

        Assertions.assertNotNull(movieResponseList);
        for (int i = 0; i < movieResponseList.size(); i++) {
            Assertions.assertEquals(movieList.getResults().get(i).getTitle(), movieResponseList.get(i).getTitle());
            Assertions.assertEquals(movieList.getResults().get(i).getId(), movieResponseList.get(i).getMovieId());
            Assertions.assertEquals(movieList.getResults().get(i).getOverview(), movieResponseList.get(i).getOverview());
            Assertions.assertEquals(movieList.getResults().get(i).getReleaseDate().substring(0, 4), movieResponseList.get(i).getReleaseYear());
            Assertions.assertEquals(movieList.getResults().get(i).getPosterPath(), movieResponseList.get(i).getPoster());
            Assertions.assertEquals(movieActorsExpected, movieResponseList.get(i).getActors());
            Assertions.assertEquals(movieGenreEnumExpected, movieResponseList.get(i).getGenrers());
            Assertions.assertEquals(buyListExpected, movieResponseList.get(i).getJustWatch().getBuy());
            Assertions.assertEquals(rentListExpected, movieResponseList.get(i).getJustWatch().getRent());
            Assertions.assertEquals(flatrateListExpected, movieResponseList.get(i).getJustWatch().getFlatrate());
        }
    }

    @Test
    @DisplayName("Should get correct response for search by movie recommendation")
    void getMovieSearchByRecommendation() {
        ResponseApiSearchBy movieList = ResponseApiSearchByBuilder.one().now();
        ResponseApiMovieCredits movieCredits = ResponseApiMovieCreditsBuilder.one().now();
        ResponseApiMovieProviders movieProviders = ResponseApiMovieProvidersBuilder.one().now();
        ParamsSearchByRecommendations paramsSearchByRecommendations = new ParamsSearchByRecommendations(null);

        Mockito.when(movieSearch.getMovieByName(null)).thenReturn(movieList);
        Mockito.when(movieSearch.getMovieCredits(any(), any())).thenReturn(movieCredits);
        Mockito.when(rentPrice.getRentPriceFromYear(any())).thenReturn(5.0);
        Mockito.when(movieSearch.getMovieWatchProviders(any(), any())).thenReturn(movieProviders);
        Mockito.when(movieSearch.getMovieByRecommendations(paramsSearchByRecommendations, 1L)).thenReturn(movieList);

        List<ResponseApiClient> movieResponseList = movieSearchProxy.getMovieByRecommendation(paramsSearchByRecommendations, 1L);
        List<String> movieActorsExpected = getMovieActorsExpected(movieCredits);
        List<GenresEnum> movieGenreEnumExpected = getMovieGenreEnumExpected();
        List<ResponseRentAndBuy> buyListExpected = getBuyListExpected(7.5);
        List<ResponseRentAndBuy> rentListExpected = getBuyListExpected(5.0);
        List<ResponseFlatrate> flatrateListExpected = getFlatrateListExpected();

        Assertions.assertNotNull(movieResponseList);
        for (int i = 0; i < movieResponseList.size(); i++) {
            Assertions.assertEquals(movieList.getResults().get(i).getTitle(), movieResponseList.get(i).getTitle());
            Assertions.assertEquals(movieList.getResults().get(i).getId(), movieResponseList.get(i).getMovieId());
            Assertions.assertEquals(movieList.getResults().get(i).getOverview(), movieResponseList.get(i).getOverview());
            Assertions.assertEquals(movieList.getResults().get(i).getReleaseDate().substring(0, 4), movieResponseList.get(i).getReleaseYear());
            Assertions.assertEquals(movieList.getResults().get(i).getPosterPath(), movieResponseList.get(i).getPoster());
            Assertions.assertEquals(movieActorsExpected, movieResponseList.get(i).getActors());
            Assertions.assertEquals(movieGenreEnumExpected, movieResponseList.get(i).getGenrers());
            Assertions.assertEquals(buyListExpected, movieResponseList.get(i).getJustWatch().getBuy());
            Assertions.assertEquals(rentListExpected, movieResponseList.get(i).getJustWatch().getRent());
            Assertions.assertEquals(flatrateListExpected, movieResponseList.get(i).getJustWatch().getFlatrate());
        }
    }

    private List<ResponseFlatrate> getFlatrateListExpected() {
        List<ResponseFlatrate> flatrateListExpected = new ArrayList<>();
        ResponseFlatrate responseFlatrateExpected = new ResponseFlatrate();
        responseFlatrateExpected.setProviderName(ProvidersEnum.NETFLIX.toString());
        flatrateListExpected.add(responseFlatrateExpected);
        return flatrateListExpected;
    }
    private List<ResponseRentAndBuy> getBuyListExpected(double price) {
        List<ResponseRentAndBuy> buyListExpected = new ArrayList<>();
        ResponseRentAndBuy buy = new ResponseRentAndBuy();
        buy.setPrice(price);
        buy.setStore(ProvidersEnum.NETFLIX.toString());
        buyListExpected.add(buy);
        return buyListExpected;
    }

    private List<GenresEnum> getMovieGenreEnumExpected() {
        List<GenresEnum> movieGenreEnumExpected = new ArrayList<>();
        movieGenreEnumExpected.add(GenresEnum.ACAO);
        movieGenreEnumExpected.add(GenresEnum.ANIMACAO);
        return movieGenreEnumExpected;
    }

    private List<String> getMovieActorsExpected(ResponseApiMovieCredits movieCredits) {
        List<String> movieActorsExpected = new ArrayList<>();
        for (int i = 0; i < movieCredits.getCast().size(); i++) {
            movieActorsExpected.add(movieCredits.getCast().get(i).getName());
        }
        return movieActorsExpected;
    }
}