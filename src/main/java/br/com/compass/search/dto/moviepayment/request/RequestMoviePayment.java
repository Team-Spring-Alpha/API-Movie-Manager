package br.com.compass.search.dto.moviepayment.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RequestMoviePayment {
    @JsonProperty("movies_id")
    private List<Long> moviesId;
    private Double amount;
    @JsonProperty("credit_card")
    private RequestMoviePaymentCreditCard creditCard;

}
