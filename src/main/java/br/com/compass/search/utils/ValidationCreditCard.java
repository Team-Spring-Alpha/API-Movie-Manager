package br.com.compass.search.utils;

import br.com.compass.search.dto.moviepayment.request.RequestMoviePaymentCreditCard;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Component
public class ValidationCreditCard {

    public void validationMoviePaymentCreditCard(RequestMoviePaymentCreditCard requestMoviePaymentCreditCard) {
        validRequestCreditCardSecurityCode(requestMoviePaymentCreditCard);
        validRequestCreditCardMonthExpiration(requestMoviePaymentCreditCard);
        validRequestCreditCardYearExpiration(requestMoviePaymentCreditCard);
    }

    private void validRequestCreditCardSecurityCode(RequestMoviePaymentCreditCard requestMoviePaymentCreditCard) {
        String regexStringSecurityCode = "[0-9]{3}";

        if (!requestMoviePaymentCreditCard.getCreditCardSecurityCode().matches(regexStringSecurityCode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private void validRequestCreditCardMonthExpiration(RequestMoviePaymentCreditCard requestMoviePaymentCreditCard) {
        String regexStringMonthExpiration = "^[1-9]{1}|^1[0-2]{1}";

        if (!requestMoviePaymentCreditCard.getCreditCardMonthExpiration().matches(regexStringMonthExpiration)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private void validRequestCreditCardYearExpiration(RequestMoviePaymentCreditCard requestMoviePaymentCreditCard) {
        String regexStringAnoExpiracao = "^[0-9]{4}";
        boolean yearCreditCardIsValid = false;

        if (requestMoviePaymentCreditCard.getCreditCardYearExpiration().matches(regexStringAnoExpiracao)) {
            int yearNow = LocalDate.now().getYear();
            int requestYear = Integer.parseInt(requestMoviePaymentCreditCard.getCreditCardYearExpiration());

            boolean requestYearIsAfterOrEqualYearNow = requestYear >= yearNow;
            boolean requestYearIsBeforeOrEqualYearNowMinusFive = requestYear <= (yearNow + 5);

            if (requestYearIsAfterOrEqualYearNow && requestYearIsBeforeOrEqualYearNowMinusFive) {
                yearCreditCardIsValid = true;
            }
        }

        if (!yearCreditCardIsValid) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
