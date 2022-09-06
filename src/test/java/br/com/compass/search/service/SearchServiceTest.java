package br.com.compass.search.service;


import br.com.compass.search.user.MovieSearchProxy;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = SearchService.class)
class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @MockBean
    private MovieSearchProxy movieSearchProxy;

    @Test
    @DisplayName("should send a request with all filters null")
    void shouldSendARequestWithAllFiltersNull() {
        ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(null);

        searchService.findByFilters(null, null, null, null, null, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(searchByFilters, null, null);
    }

    @Test
    @DisplayName("should send a request with Genre Filter")
    void shouldSendARequestWithGenreFilter() {
        ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(null);
        searchByFilters.setWith_genres(GenresEnum.ACAO.getIdGenrer());

        searchService.findByFilters(GenresEnum.ACAO, null, null, null, null, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(searchByFilters, null, null);
    }

    @Test
    @DisplayName("should send a request with movie provider Filter")
    void shouldSendARequestWithMovieProviderFilter() {
        ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(null);
        searchByFilters.setWith_watch_providers(ProvidersEnum.NETFLIX.getIdProvider());

        searchService.findByFilters(null, null, null, ProvidersEnum.NETFLIX, null, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(searchByFilters, null, null);
    }

    @Test
    @DisplayName("should send a request with date Filter")
    void shouldSendARequestWithDateFilter() {
        ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(null);
        LocalDate dateNowMinusOneYear = LocalDate.now().minusYears(1);
        LocalDate dateNow = LocalDate.now();

        searchService.findByFilters(null, dateNowMinusOneYear, dateNow, null, null, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(searchByFilters, dateNowMinusOneYear.toString(), dateNow.toString());
    }

    @Test
    @DisplayName("should send a request with actor name Filter")
    void shouldSendARequestWithActorNameFilter() {
        ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(null);

        List<String> actorList = new ArrayList<>();
        actorList.add("test");
        actorList.add("test 2");

        List<Long> ActorlongList = new ArrayList<>();
        ActorlongList.add(5L);
        ActorlongList.add(8L);
        searchByFilters.setWith_people(ActorlongList);

        Mockito.when(movieSearchProxy.actorsStringToActorsId(actorList)).thenReturn(ActorlongList);

        searchService.findByFilters(null, null, null, null, actorList, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(searchByFilters, null,null);
    }

    @DisplayName("should send a request with movie name filter")
    @Test
    void shouldSendARequestWithMovieNameFilter(){
        ParamsSearchByName searchByName = new ParamsSearchByName(null, "test title");

        searchService.findByFilters(null, null, null, null, null, "test title");

        Mockito.verify(movieSearchProxy).getMovieSearchByName(searchByName);
    }

    @Test
    @DisplayName("Should send a request with movie recommendations")
    void shouldSendARequestWithMovieIdRecommendations() {
        Long movieId= 1L;
        ParamsSearchByRecommendations searchByRecommendations = new ParamsSearchByRecommendations(null);
        searchService.findMoviesRecommendations(movieId);

        Mockito.verify(movieSearchProxy).getMovieByRecommendation(searchByRecommendations, movieId);
        Mockito.validateMockitoUsage();
    }
}