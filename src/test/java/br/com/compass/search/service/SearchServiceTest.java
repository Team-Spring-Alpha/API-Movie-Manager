package br.com.compass.search.service;

import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.enums.ProvidersEnum;
import br.com.compass.search.proxy.MovieSearchProxy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SearchService.class)
class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @MockBean
    private MovieSearchProxy movieSearchProxy;


    @Test
    void findByFilters() {
        searchService.findByFilters(GenresEnum.ACAO, LocalDate.now(), LocalDate.now(), ProvidersEnum.AMAZON_PRIME);
    }
}