package br.com.compass.search.dto.moviepayment.response;

import br.com.compass.search.enums.MoviePaymentStatus;
import lombok.Data;

import java.util.List;

@Data
public class ResponseMoviePayment {
    private String moviePaymentOrderId;
    private List<Long> moviesId;
    private Double amount;
    private MoviePaymentStatus status = MoviePaymentStatus.PROCESSING;

}
