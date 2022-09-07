package br.com.compass.search.service;

import org.springframework.stereotype.Component;

@Component
public class RentPrice {

    public Double getRentPriceFromYear(String yearRelease) {
        Double rentPrice = 0.0;
        try {
            int year = Integer.parseInt(yearRelease);
            if (year < 1990) {
                rentPrice = 5.0;
            } else if (year <= 2000) {
                rentPrice = 10.0;
            } else if (year <= 2010) {
                rentPrice = 20.0;
            } else {
                rentPrice = 30.0;
            }
        } catch (NumberFormatException exception) {
            rentPrice = 15.0;
        }

        return rentPrice;
    }
}
