package br.com.compass.search.utils;

import org.springframework.stereotype.Component;

@Component
public class RentPrice {

    public Double getRentPriceFromYear(String yearRelease) {
        Double rentPrice = 0.0;
        int year = Integer.parseInt(yearRelease);
        if (year < 1990) {
            rentPrice = 5.0;
        } else if (year >= 1990 && year <= 2000) {
            rentPrice = 10.0;
        } else if (year > 2000 && year <= 2010) {
            rentPrice = 20.0;
        } else {
            rentPrice = 30.0;
        }
        return rentPrice;
    }
}
