package br.com.compass.search.controller;

import br.com.compass.search.dto.moviepayment.request.RequestMoviePayment;
import br.com.compass.search.dto.moviepayment.response.ResponseMoviePayment;
import br.com.compass.search.service.MoviePaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@Controller
@RequestMapping("movie-payment")
@RequiredArgsConstructor
public class MoviePaymentController {

    private final MoviePaymentService moviePaymentService;

    @PostMapping
    public ResponseEntity<ResponseMoviePayment> postMoviePayment(@Valid @RequestBody RequestMoviePayment moviePayment, UriComponentsBuilder uriComponentsBuilder) {
        ResponseMoviePayment responseMoviePayment = moviePaymentService.postMoviePayment(moviePayment);

        URI uri = uriComponentsBuilder.path("/movie-payment/{id}").buildAndExpand(responseMoviePayment.getMoviePaymentOrderId()).toUri();
        return ResponseEntity.created(uri).body(responseMoviePayment);
    }

    @GetMapping("/{moviePaymentOrderId}")
    public ResponseEntity<ResponseMoviePayment> getByOrderId(@PathVariable String moviePaymentOrderId) {
        ResponseMoviePayment responseMoviePayment = moviePaymentService.getById(moviePaymentOrderId);
        return ResponseEntity.ok(responseMoviePayment);
    }
}
