package br.com.compass.search.service;

import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchBy;
import br.com.compass.search.utils.ModelMapperUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SearchService.class)
@AutoConfigure
class SearchServiceTest {

    @Autowired
    private SearchService searchService;
    @MockBean
    private WebClient.Builder webBuilder;

    @MockBean
    private ModelMapperUtils modelMapperUtils;

    @Test
    void findByDate() {
        ResponseApiSearchBy responseApiSearchBy = new ResponseApiSearchBy();




        Assertions.assertNotNull(responseApiSearchBy);
    }
}