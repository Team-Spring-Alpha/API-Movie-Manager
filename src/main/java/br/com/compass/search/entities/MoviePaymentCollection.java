package br.com.compass.search.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Document(collection = "Movie_Payment")
public class MoviePaymentCollection {
    @Id
    private String moviePaymentOrderId;
    @Field(name = "movies_id")
    private List<Long> moviesId;
    private Double amount;
    @Field(name = "credit_card")
    private MoviePaymentCreditCard creditCard;
}
