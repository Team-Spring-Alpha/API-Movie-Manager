package br.com.compass.search.service;

import br.com.compass.search.dto.moviepayment.request.RequestMoviePayment;
import br.com.compass.search.dto.moviepayment.response.ResponseMoviePayment;
import br.com.compass.search.entities.MoviePaymentCollection;
import br.com.compass.search.repository.MoviePaymentRepository;
import br.com.compass.search.utils.ValidationCreditCard;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class MoviePaymentService {

    private final MoviePaymentRepository moviePaymentRepository;

    private final ValidationCreditCard validationCreditCard;

    private final ModelMapper modelMapper;

    public ResponseMoviePayment postMoviePayment(RequestMoviePayment requestMoviePayment) {
        validationCreditCard.validationMoviePaymentCreditCard(requestMoviePayment.getCreditCard());
        MoviePaymentCollection moviePaymentCollection = modelMapper.map(requestMoviePayment, MoviePaymentCollection.class);
        MoviePaymentCollection savedMoviePaymentCollection = moviePaymentRepository.save(moviePaymentCollection);

        return modelMapper.map(savedMoviePaymentCollection, ResponseMoviePayment.class);
    }


    public ResponseMoviePayment getById(String moviePaymentOrderId) {
        MoviePaymentCollection moviePaymentCollection = moviePaymentRepository.findById(moviePaymentOrderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return modelMapper.map(moviePaymentCollection, ResponseMoviePayment.class);
    }
}
