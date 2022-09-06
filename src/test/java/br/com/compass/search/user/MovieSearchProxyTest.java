package br.com.compass.search.user;

import br.com.compass.search.builders.ResponseApiMovieCreditsBuilder;
import br.com.compass.search.builders.ResponseApiMovieProvidersBuilder;
import br.com.compass.search.builders.ResponseApiResultActorBuilder;
import br.com.compass.search.builders.ResponseApiSearchByBuilder;
import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResultActorDTO;
import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCreditsDTO;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieProvidersDTO;
import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchByDTO;
import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiSearchByActorDTO;
import br.com.compass.search.dto.apiclient.response.ResponseApiUserDTO;
import br.com.compass.search.dto.apiclient.response.ResponseFlatrateDTO;
import br.com.compass.search.dto.apiclient.response.ResponseRentAndBuyDTO;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import br.com.compass.search.service.RentPriceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = MovieSearchProxy.class)
class MovieSearchProxyTest {

    @Autowired
    private MovieSearchProxy movieSearchProxy;

    @MockBean
    private MovieSearch movieSearch;

    @MockBean
    private RentPriceService rentPriceService;

    @Test
    @DisplayName("should get response api client correct")
    void getMovieSearchByFilters() {
        ResponseApiSearchByDTO movieList = ResponseApiSearchByBuilder.one().now();
        ResponseApiMovieCreditsDTO movieCredits = ResponseApiMovieCreditsBuilder.one().now();
        ResponseApiMovieProvidersDTO movieProviders = ResponseApiMovieProvidersBuilder.one().now();

        Mockito.when(movieSearch.getMovieByFilters(null, null, null)).thenReturn(movieList);
        Mockito.when(movieSearch.getMovieCredits(any(), any())).thenReturn(movieCredits);
        Mockito.when(rentPriceService.getRentPriceFromYear(any())).thenReturn(5.0);
        Mockito.when(movieSearch.getMovieWatchProviders(any(), any())).thenReturn(movieProviders);

        HashSet<ResponseApiUserDTO> movieResponseHashset = movieSearchProxy.getMovieSearchByFilters(null, null, null);
        List<ResponseApiUserDTO> movieResponseList = new ArrayList<>(movieResponseHashset);


        List<String> movieActorsExpected = getMovieActorsExpected(movieCredits);
        List<GenresEnum> movieGenreEnumExpected = getMovieGenreEnumExpected();
        List<ResponseRentAndBuyDTO> buyListExpected = getBuyListExpected(7.5);
        List<ResponseRentAndBuyDTO> rentListExpected = getBuyListExpected(5.0);
        List<ResponseFlatrateDTO> flatrateListExpected = getFlatrateListExpected();

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
        ResponseApiSearchByDTO movieList = ResponseApiSearchByBuilder.one().now();
        ResponseApiMovieCreditsDTO movieCredits = ResponseApiMovieCreditsBuilder.one().now();
        ResponseApiMovieProvidersDTO movieProviders = ResponseApiMovieProvidersBuilder.one().now();

        Mockito.when(movieSearch.getMovieByName(null)).thenReturn(movieList);
        Mockito.when(movieSearch.getMovieCredits(any(), any())).thenReturn(movieCredits);
        Mockito.when(rentPriceService.getRentPriceFromYear(any())).thenReturn(5.0);
        Mockito.when(movieSearch.getMovieWatchProviders(any(), any())).thenReturn(movieProviders);

        HashSet<ResponseApiUserDTO> movieResponseHashset= movieSearchProxy.getMovieSearchByName(null);

        List<ResponseApiUserDTO> movieResponseList = new ArrayList<>(movieResponseHashset);

        List<String> movieActorsExpected = getMovieActorsExpected(movieCredits);
        List<GenresEnum> movieGenreEnumExpected = getMovieGenreEnumExpected();
        List<ResponseRentAndBuyDTO> buyListExpected = getBuyListExpected(7.5);
        List<ResponseRentAndBuyDTO> rentListExpected = getBuyListExpected(5.0);
        List<ResponseFlatrateDTO> flatrateListExpected = getFlatrateListExpected();

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
        ResponseApiSearchByDTO movieList = ResponseApiSearchByBuilder.one().now();
        ResponseApiMovieCreditsDTO movieCredits = ResponseApiMovieCreditsBuilder.one().now();
        ResponseApiMovieProvidersDTO movieProviders = ResponseApiMovieProvidersBuilder.one().now();
        ParamsSearchByRecommendations paramsSearchByRecommendations = new ParamsSearchByRecommendations(null);

        Mockito.when(movieSearch.getMovieByName(null)).thenReturn(movieList);
        Mockito.when(movieSearch.getMovieCredits(any(), any())).thenReturn(movieCredits);
        Mockito.when(rentPriceService.getRentPriceFromYear(any())).thenReturn(5.0);
        Mockito.when(movieSearch.getMovieWatchProviders(any(), any())).thenReturn(movieProviders);
        Mockito.when(movieSearch.getMovieByRecommendations(paramsSearchByRecommendations, 1L)).thenReturn(movieList);

        HashSet<ResponseApiUserDTO> movieResponseHashset = movieSearchProxy.getMovieByRecommendation(paramsSearchByRecommendations, 1L);
        List<ResponseApiUserDTO> movieResponseList = new ArrayList<>(movieResponseHashset);

        List<String> movieActorsExpected = getMovieActorsExpected(movieCredits);
        List<GenresEnum> movieGenreEnumExpected = getMovieGenreEnumExpected();
        List<ResponseRentAndBuyDTO> buyListExpected = getBuyListExpected(7.5);
        List<ResponseRentAndBuyDTO> rentListExpected = getBuyListExpected(5.0);
        List<ResponseFlatrateDTO> flatrateListExpected = getFlatrateListExpected();

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
    @DisplayName("should get actors id by name when acting")
    void shouldGetActorsIdByNameWhenActing() {
        List<String> actorListName = new ArrayList<>();
        actorListName.add("test");
        actorListName.add("test 2");

        ResponseApiResultActorDTO resultActor = ResponseApiResultActorBuilder.one().withId(1L).withKnownForDepartment("Acting").now();
        List<ResponseApiResultActorDTO> resultActorList = new ArrayList<>();
        resultActorList.add(resultActor);

        ResponseApiSearchByActorDTO responseApiSearchByActorDTO = new ResponseApiSearchByActorDTO();
        responseApiSearchByActorDTO.setResults(resultActorList);

        Mockito.when(movieSearch.getMoviesByActors(any())).thenReturn(responseApiSearchByActorDTO);

        List<Long> actorsId = movieSearchProxy.actorsStringToActorsId(actorListName);

        for (Long aLong : actorsId) {
            Assertions.assertEquals(1L, aLong);
        }
    }

    @Test
    @DisplayName("shouldnt get actors id by name when not acting")
    void shouldntGetActorsIdByNameWhenNotActing() {
        List<String> actorListName = new ArrayList<>();
        actorListName.add("test");
        actorListName.add("test 2");

        ResponseApiResultActorDTO resultActor = ResponseApiResultActorBuilder.one().withId(1L).withKnownForDepartment("test").now();
        List<ResponseApiResultActorDTO> resultActorList = new ArrayList<>();
        resultActorList.add(resultActor);

        ResponseApiSearchByActorDTO responseApiSearchByActorDTO = new ResponseApiSearchByActorDTO();
        responseApiSearchByActorDTO.setResults(resultActorList);

        Mockito.when(movieSearch.getMoviesByActors(any())).thenReturn(responseApiSearchByActorDTO);

        List<Long> actorsId = movieSearchProxy.actorsStringToActorsId(actorListName);

        boolean actorsIdEmpty = actorsId.isEmpty();

        Assertions.assertTrue(actorsIdEmpty);
    }



    private List<ResponseFlatrateDTO> getFlatrateListExpected() {
        List<ResponseFlatrateDTO> flatrateListExpected = new ArrayList<>();
        ResponseFlatrateDTO responseFlatrateDTOExpected = new ResponseFlatrateDTO();
        responseFlatrateDTOExpected.setProviderName(ProvidersEnum.NETFLIX.toString());
        flatrateListExpected.add(responseFlatrateDTOExpected);
        return flatrateListExpected;
    }
    private List<ResponseRentAndBuyDTO> getBuyListExpected(double price) {
        List<ResponseRentAndBuyDTO> buyListExpected = new ArrayList<>();
        ResponseRentAndBuyDTO buy = new ResponseRentAndBuyDTO();
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

    private List<String> getMovieActorsExpected(ResponseApiMovieCreditsDTO movieCredits) {
        List<String> movieActorsExpected = new ArrayList<>();
        for (int i = 0; i < movieCredits.getCast().size(); i++) {
            movieActorsExpected.add(movieCredits.getCast().get(i).getName());
        }
        return movieActorsExpected;
    }
}