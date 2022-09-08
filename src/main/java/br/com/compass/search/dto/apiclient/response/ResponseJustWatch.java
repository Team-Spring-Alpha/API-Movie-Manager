package br.com.compass.search.dto.apiclient.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ResponseJustWatch {
    @ApiModelProperty(position = 1)
    private List<ResponseRentAndBuy> rent;
    @ApiModelProperty(position = 2)
    private List<ResponseRentAndBuy> buy;
    @ApiModelProperty(position = 3)
    private List<ResponseFlatrate> flatrate;
}
