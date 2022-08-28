package br.com.compass.search.proxy;

import br.com.compass.search.utils.RentPrice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = MovieSearchProxy.class)
class MovieSearchProxyTest {

    @Autowired
    private MovieSearchProxy movieSearchProxy;

    @MockBean
    private MovieSearch movieSearch;

    @MockBean
    private RentPrice rentPrice;

    @Test
    void getMovieSearchByFilters() {

    }
}