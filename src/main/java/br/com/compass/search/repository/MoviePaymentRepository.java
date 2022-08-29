package br.com.compass.search.repository;

import br.com.compass.search.entities.MoviePaymentCollection;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoviePaymentRepository extends MongoRepository<MoviePaymentCollection, String> {
}
