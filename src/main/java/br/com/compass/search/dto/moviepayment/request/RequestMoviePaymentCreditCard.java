package br.com.compass.search.dto.moviepayment.request;

import br.com.compass.search.enums.CreditCardBrandEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RequestMoviePaymentCreditCard {
    @CreditCardNumber
    @NotBlank
    @JsonProperty("card_number")
    private String creditCardNumber;
    @NotBlank
    @JsonProperty("card_brand")
    private CreditCardBrandEnum creditCardBrand;
    @NotBlank
    @JsonProperty("card_security_code")
    private String creditCardSecurityCode;
    @NotBlank
    @JsonProperty("card_year_expiration")
    private String creditCardYearExpiration;
    @NotBlank
    @JsonProperty("card_month_expiration")
    private String creditCardMonthExpiration;
    @NotBlank
    @Pattern(regexp = "^[A-Z][A-Za-z ]*$", message = "Only letters can be used. And should be capitalized")
    @JsonProperty("card_holder_name")
    private String creditCardHolderName;

}
