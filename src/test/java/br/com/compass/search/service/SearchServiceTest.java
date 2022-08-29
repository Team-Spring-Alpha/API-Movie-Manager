package br.com.compass.search.service;


import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import br.com.compass.search.proxy.MovieSearchProxy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

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

        searchService.findByFilters(null, null, null, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(searchByFilters, null, null);
    }

    @Test
    @DisplayName("should send a request with Genre Filter")
    void shouldSendARequestWithGenreFilter() {
        ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(null);
        searchByFilters.setWith_genres(GenresEnum.ACAO.getIdGenrer());

        searchService.findByFilters(GenresEnum.ACAO, null, null, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(searchByFilters, null, null);
    }

    @Test
    @DisplayName("should send a request with movie provider Filter")
    void shouldSendARequestWithMovieProviderFilter() {
        ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(null);
        searchByFilters.setWith_watch_providers(ProvidersEnum.NETFLIX.getIdProvider());

        searchService.findByFilters(null, null, null, ProvidersEnum.NETFLIX);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(searchByFilters, null, null);
    }

    @Test
    @DisplayName("should send a request with date Filter")
    void shouldSendARequestWithDateFilter() {
        ParamsSearchByFilters searchByFilters = new ParamsSearchByFilters(null);
        LocalDate dateNowMinusOneYear = LocalDate.now().minusYears(1);
        LocalDate dateNow = LocalDate.now();

        searchService.findByFilters(null, dateNowMinusOneYear, dateNow, null);

        Mockito.verify(movieSearchProxy).getMovieSearchByFilters(searchByFilters, dateNowMinusOneYear.toString(), dateNow.toString());
    }

    @Test
    @DisplayName("should send a request with name filter")
    void shouldSendARequestWithNameFilter() {
        ParamsSearchByName searchByName = new ParamsSearchByName(null, "star");
        searchService.findByName("star");

        Mockito.verify(movieSearchProxy).getMovieSearchByName(searchByName);
    }

    @Test
    @DisplayName("should send a request with name filter is null")
    void shouldSendARequestWithNameFilterIsNull() {
        ParamsSearchByName searchByName = new ParamsSearchByName(null, null);
        searchService.findByName(null);

        Mockito.verify(movieSearchProxy).getMovieSearchByName(searchByName);
    }
}