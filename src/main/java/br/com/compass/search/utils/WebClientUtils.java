package br.com.compass.search.utils;

import br.com.compass.search.dto.apiclient.response.ResponseFlatrate;
import br.com.compass.search.dto.apiclient.response.ResponseJustWatch;
import br.com.compass.search.dto.apiclient.response.ResponseRentAndBuy;
import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCredits;
import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCreditsCast;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieProviders;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class WebClientUtils {

    private final WebClient.Builder webBuider;

    @Value("${API_KEY}")
    private String apiKey;

    public List<String> getActorsByMovieId(Long id) {
        List<String> actorsList = new ArrayList<>();

        int actorListSize = 3;

        ResponseApiMovieCredits responseApiMovieCredits = webBuider.build().get().uri(uriBuilder -> uriBuilder
                .scheme("https").host("api.themoviedb.org")
                .path("/3/movie/" + id + "/credits")
                .queryParam("language", "pt-BR")
                .queryParam("api_key", apiKey)
                .build())
                .retrieve()
                .bodyToMono(ResponseApiMovieCredits.class).block();

        for (int i = 0; i < Objects.requireNonNull(responseApiMovieCredits).getCast().size(); i++) {
            ResponseApiMovieCreditsCast cast = responseApiMovieCredits.getCast().get(i);

            if (cast.getKnownForDepartment().equals("Acting")) {
                actorsList.add(cast.getName());
                actorListSize--;
            }

            if (actorListSize == 0) {
                break;
            }
        }
        return actorsList;
    }

    public ResponseJustWatch getJustWatchDataFromMovieIdAndRentPrice(Long id, Double rentPrice) {
        ResponseApiMovieProviders responseApiMovieProviders = webBuider.build().get().uri(uriBuilder -> uriBuilder
                        .scheme("https").host("api.themoviedb.org")
                        .path("/3/movie/" + id + "/watch/providers")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(ResponseApiMovieProviders.class).block();

        if (responseApiMovieProviders.getResults().getBr() == null) {
            return null;
        }

        ResponseJustWatch responseJustWatch = new ResponseJustWatch();
        List<ResponseRentAndBuy> responseBuyList = new ArrayList<>();
        List<ResponseRentAndBuy> responseRentList = new ArrayList<>();
        List<ResponseFlatrate> responseFlatrateList = new ArrayList<>();

        if (responseApiMovieProviders.getResults().getBr().getBuy() != null) {
            for (int i = 0; i < responseApiMovieProviders.getResults().getBr().getBuy().size(); i++) {
                ResponseRentAndBuy buy = new ResponseRentAndBuy();
                buy.setStore(responseApiMovieProviders.getResults().getBr().getBuy().get(i).getProviderName());
                buy.setPrice(rentPrice * 1.5);
                responseBuyList.add(buy);
            }
        }

        if (responseApiMovieProviders.getResults().getBr().getRent() != null) {
            for (int i = 0; i < responseApiMovieProviders.getResults().getBr().getRent().size(); i++) {
                ResponseRentAndBuy rent = new ResponseRentAndBuy();
                rent.setStore(responseApiMovieProviders.getResults().getBr().getRent().get(i).getProviderName());
                rent.setPrice(rentPrice);
                responseRentList.add(rent);
            }
        }

        if (responseApiMovieProviders.getResults().getBr().getFlatrate() != null) {
            for (int i = 0; i < responseApiMovieProviders.getResults().getBr().getFlatrate().size(); i++) {
                ResponseFlatrate responseFlatrate = new ResponseFlatrate();
                responseFlatrate.setProviderName(responseApiMovieProviders.getResults().getBr().getFlatrate().get(i).getProviderName());
                responseFlatrateList.add(responseFlatrate);
            }
        }


        responseJustWatch.setFlatrate(responseFlatrateList);
        responseJustWatch.setBuy(responseBuyList);
        responseJustWatch.setRent(responseRentList);
        return responseJustWatch;
    }

}
