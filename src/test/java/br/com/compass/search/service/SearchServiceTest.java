package br.com.compass.search.service;

import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchBy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(classes = SearchService.class)
class SearchServiceTest {

    @Autowired
    private SearchService searchService;
    @MockBean
    private WebClient.Builder webBuilder;

    @Test
    void findByDate() {
        ResponseApiSearchBy responseApiSearchBy = new ResponseApiSearchBy();
        Mockito.when(webBuilder.build().get());



        Assertions.assertNotNull(responseApiSearchBy);
    }
}