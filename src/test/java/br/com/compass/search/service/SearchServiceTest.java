package br.com.compass.search.service;


import br.com.compass.search.client.MovieSearchProxy;
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

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = SearchService.class)
class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @MockBean
    private MovieSearchProxy movieSearchProxy;

    @Test
    @DisplayName("should send a request with all filters null")
    void shouldSendARequestWithAllFiltersNull() {

        searchService.findByFilters(null, null, null, null, null, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(null, null, null, null, null);

        Mockito.verify(movieSearchProxy, Mockito.never()).getMovieSearchByName(any());
    }

    @Test
    @DisplayName("should send a request with Genre Filter")
    void shouldSendARequestWithGenreFilter() {
        searchService.findByFilters(GenresEnum.ACAO, null, null, null, null, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(GenresEnum.ACAO.getIdGenrer(), null, null, null, null);

        Mockito.verify(movieSearchProxy, Mockito.never()).getMovieSearchByName(any());
    }

    @Test
    @DisplayName("should send a request with movie provider Filter")
    void shouldSendARequestWithMovieProviderFilter() {
        searchService.findByFilters(null, null, null, ProvidersEnum.NETFLIX, null, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(null, ProvidersEnum.NETFLIX.getIdProvider(), null, null, null);

        Mockito.verify(movieSearchProxy, Mockito.never()).getMovieSearchByName(any());
    }

    @Test
    @DisplayName("should send a request with date Filter")
    void shouldSendARequestWithDateFilter() {
        LocalDate dateNowMinusOneYear = LocalDate.now().minusYears(1);
        LocalDate dateNow = LocalDate.now();

        searchService.findByFilters(null, dateNowMinusOneYear, dateNow, null, null, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(null, null, null, dateNowMinusOneYear.toString(), dateNow.toString());

        Mockito.verify(movieSearchProxy, Mockito.never()).getMovieSearchByName(any());
    }

    @Test
    @DisplayName("should send a request with actor name Filter")
    void shouldSendARequestWithActorNameFilter() {
        List<String> actorListName = new ArrayList<>();
        actorListName.add("test");
        actorListName.add("test 2");

        List<Long> actorlongListId = new ArrayList<>();
        actorlongListId.add(5L);
        actorlongListId.add(8L);

        Mockito.when(movieSearchProxy.actorsStringToActorsId(actorListName)).thenReturn(actorlongListId);

        searchService.findByFilters(null, null, null, null, actorListName, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(null, null, actorlongListId, null, null);

        Mockito.verify(movieSearchProxy, Mockito.never()).getMovieSearchByName(any());
    }

    @DisplayName("should send a request with movie name filter and another filter")
    @Test
    void shouldSendARequestWithMovieNameFilterAndAnotherFilter(){
        String movieName = "test title";
        searchService.findByFilters(null, null, null, null, null, movieName);

        Mockito.verify(movieSearchProxy).getMovieSearchByName(movieName);

        Mockito.verify(movieSearchProxy, Mockito.never()).getMovieSearchByFilters(null, null, null, null, null);
    }

    @DisplayName("should send a request with movie name filter")
    @Test
    void shouldSendARequestWithMovieNameFilter(){
        String movieName = "test title";
        searchService.findByFilters(null, null, null, null, null, movieName);

        Mockito.verify(movieSearchProxy).getMovieSearchByName(movieName);
        Mockito.verify(movieSearchProxy, Mockito.never()).getMovieSearchByFilters(null, ProvidersEnum.NETFLIX.getIdProvider(), null, null, null);
    }

    @Test
    @DisplayName("Should send a request with movie recommendations")
    void shouldSendARequestWithMovieIdRecommendations() {
        Long movieId = 1L;
        searchService.findMoviesRecommendations(movieId);

        Mockito.verify(movieSearchProxy).getMovieByRecommendation(movieId);
    }

    @Test
    @DisplayName("should get movie by id")
    void shouldGetMovieById() {
        Long movieId = 1L;
        searchService.findByMovieId(movieId);

        Mockito.verify(movieSearchProxy).getMovieById(movieId);
    }
}