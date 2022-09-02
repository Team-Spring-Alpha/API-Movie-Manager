package br.com.compass.search.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RentPrice.class)
class RentPriceTest {

    @Autowired
    private RentPrice rentPrice;

    @Test
    @DisplayName("should get rent price of 5.0 when release date is before 1990")
    void shouldGetRentPriceOf5WhenReleaseDateIsBefore1990() {
        String releaseDate = "1989";

        Double rentPriceFromYear = rentPrice.getRentPriceFromYear(releaseDate);

        Assertions.assertEquals(5.0, rentPriceFromYear);
    }

    @Test
    @DisplayName("should get rent price of 10.0 when release date is between 1990 and 2000")
    void shouldGetRentPriceOf10WhenReleaseDateIsBetween1990And2000() {
        String releaseDateShortestInterval = "1990";
        String releaseDateLongestInterval = "2000";

        Double rentPriceFromShortestInterval = rentPrice.getRentPriceFromYear(releaseDateShortestInterval);
        Double rentPriceFromLongestInterval = rentPrice.getRentPriceFromYear(releaseDateLongestInterval);

        Assertions.assertEquals(10.0, rentPriceFromShortestInterval);
        Assertions.assertEquals(10.0, rentPriceFromLongestInterval);
    }

    @Test
    @DisplayName("should get rent price of 20.0 when release date is between 2001 and 2010")
    void shouldGetRentPriceOf20WhenReleaseDateIsBetween2001And2010() {
        String releaseDateShortestInterval = "2001";
        String releaseDateLongestInterval = "2010";

        Double rentPriceFromShortestInterval = rentPrice.getRentPriceFromYear(releaseDateShortestInterval);
        Double rentPriceFromLongestInterval = rentPrice.getRentPriceFromYear(releaseDateLongestInterval);

        Assertions.assertEquals(20.0, rentPriceFromShortestInterval);
        Assertions.assertEquals(20.0, rentPriceFromLongestInterval);
    }

    @Test
    @DisplayName("should get rent price of 30.0 when release date is after 2010")
    void shouldGetRentPriceOf30WhenReleaseDateIsAfter2010() {
        String releaseDate = "2011";

        Double rentPriceFromYear = rentPrice.getRentPriceFromYear(releaseDate);

        Assertions.assertEquals(30.0, rentPriceFromYear);
    }

    @Test
    @DisplayName("should get rent price of 15.0 when release date is null or parse wrong date")
    void shouldGetRentPriceOf15WhenSomethingWrongHappenWhenParseReleaseDate() {

        Double rentPriceFromNullYear = rentPrice.getRentPriceFromYear(null);
        Double rentPriceFromWrongYear = rentPrice.getRentPriceFromYear("teste");

        Assertions.assertEquals(15.0, rentPriceFromNullYear);
        Assertions.assertEquals(15.0, rentPriceFromWrongYear);
    }
}