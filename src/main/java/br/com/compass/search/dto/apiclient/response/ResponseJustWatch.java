package br.com.compass.search.dto.apiclient.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseJustWatch {
    private List<ResponseRentAndBuy> rent;
    private List<ResponseRentAndBuy> buy;
    private List<ResponseFlatrate> flatrate;
}
