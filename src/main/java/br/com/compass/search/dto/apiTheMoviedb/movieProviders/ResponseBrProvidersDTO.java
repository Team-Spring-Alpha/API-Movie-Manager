package br.com.compass.search.dto.apiTheMoviedb.movieProviders;

import lombok.Data;

import java.util.List;

@Data
public class ResponseBrProvidersDTO {
    private String link;
    private List<ResponseApiMovieRentBuyDTO> rent;
    private List<ResponseApiMovieRentBuyDTO> buy;
    private List<ResponseApiMovieRentBuyDTO> flatrate;
}
