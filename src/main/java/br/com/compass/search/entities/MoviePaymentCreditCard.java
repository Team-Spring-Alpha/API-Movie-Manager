package br.com.compass.search.entities;

import br.com.compass.search.enums.CreditCardBrandEnum;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class MoviePaymentCreditCard {
    @Field(name = "card_number")
    private String creditCardNumber;
    @Field(name = "card_brand")
    private CreditCardBrandEnum creditCardBrand;
    @Field(name = "card_security_code")
    private String creditCardSecurityCode;
    @Field(name = "card_year_expiration")
    private String creditCardYearExpiration;
    @Field(name = "card_month_expiration")
    private String creditCardMonthExpiration;
    @Field(name = "card_holder_name")
    private String creditCardHolderName;

}
