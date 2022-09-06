package br.com.compass.search.dto.apiclient.response;

import lombok.Data;

import java.util.List;

@Data
public class ResponseJustWatchDTO {
    private List<ResponseRentAndBuyDTO> rent;
    private List<ResponseRentAndBuyDTO> buy;
    private List<ResponseFlatrateDTO> flatrate;
}
