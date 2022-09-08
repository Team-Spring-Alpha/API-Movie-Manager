package br.com.compass.search.dto.apiclient.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResponseRentAndBuy {
    @ApiModelProperty(position = 1)
    private String store;
    @ApiModelProperty(position = 2)
    private Double price;
}
