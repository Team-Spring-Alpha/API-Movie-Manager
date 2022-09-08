package br.com.compass.search.user;

import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResultDTO;
import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResultActorDTO;
import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCreditsDTO;
import br.com.compass.search.dto.apiTheMoviedb.movieCredits.ResponseApiMovieCreditsCastDTO;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.Params;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByFilters;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByName;
import br.com.compass.search.dto.apiTheMoviedb.movieParams.ParamsSearchByRecommendations;
import br.com.compass.search.dto.apiTheMoviedb.movieProviders.ResponseApiMovieProvidersDTO;
import br.com.compass.search.dto.apiTheMoviedb.searchBy.ResponseApiSearchByDTO;
import br.com.compass.search.dto.apiTheMoviedb.searchByActor.ResponseApiSearchByActorDTO;
import br.com.compass.search.dto.apiclient.response.*;
import br.com.compass.search.enums.GenresEnum;
import br.com.compass.search.service.RentPriceService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MovieSearchProxy {

    @Autowired
    private MovieSearch movieSearch;

    @Value("${API_KEY}")
    private String apiKey;
    private final RentPriceService rentPriceService;

    public HashSet<ResponseApiUserDTO> getMovieSearchByName(ParamsSearchByName searchByName) {
        try {
            ResponseApiSearchByDTO movieByName = movieSearch.getMovieByName(searchByName);
            return buildResponseClientList(movieByName);
        }catch (FeignException.FeignClientException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public HashSet<ResponseApiUserDTO> getMovieSearchByFilters(ParamsSearchByFilters searchByFilters, String releaseDateAfter, String releaseDateBefore) {
        try {
            ResponseApiSearchByDTO movieByFilters = movieSearch.getMovieByFilters(searchByFilters, releaseDateAfter, releaseDateBefore);
            return buildResponseClientList(movieByFilters);
        }catch (FeignException.FeignClientException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public HashSet<ResponseApiUserDTO> getMovieByRecommendation(ParamsSearchByRecommendations byRecommendations, Long movieId) {
        try {
            ResponseApiSearchByDTO movieByRecommendations = movieSearch.getMovieByRecommendations(byRecommendations, movieId);
            return buildResponseClientList(movieByRecommendations);
        }catch (FeignException.FeignClientException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private ResponseJustWatchDTO getMovieJustWatch(Long movieId, Double rentPrice, Params params) {

        ResponseApiMovieProvidersDTO moviesWatchProviders = movieSearch.getMovieWatchProviders(params, movieId);

        if (moviesWatchProviders.getResults().getBr() == null) {
            return null;
        }

        ResponseJustWatchDTO responseJustWatchDTO = new ResponseJustWatchDTO();

        if (moviesWatchProviders.getResults().getBr().getBuy() != null) {
            List<ResponseRentAndBuyDTO> responseBuyList = new ArrayList<>();

            for (ResponseRentAndBuyDTO buy : responseBuyList) {
                int i = 0;
                buy.setStore(moviesWatchProviders.getResults().getBr().getBuy().get(i).getProviderName());
                buy.setPrice(rentPrice * 1.5);
                responseBuyList.add(buy);
                i++;
            }
            responseJustWatchDTO.setBuy(responseBuyList);
        }

        if (moviesWatchProviders.getResults().getBr().getRent() != null) {
            List<ResponseRentAndBuyDTO> responseRentList = new ArrayList<>();

            for (ResponseRentAndBuyDTO rent : responseRentList) {
                int i =0;
                rent.setStore(moviesWatchProviders.getResults().getBr().getRent().get(i).getProviderName());
                rent.setPrice(rentPrice);
                responseRentList.add(rent);
                i++;
            }
            responseJustWatchDTO.setRent(responseRentList);
        }

        if (moviesWatchProviders.getResults().getBr().getFlatrate() != null) {
            List<ResponseFlatrateDTO> responseFlatrateDTOList = new ArrayList<>();

            for (ResponseFlatrateDTO response : responseFlatrateDTOList) {
                int i=0;
                response.setProviderName(moviesWatchProviders.getResults().getBr().getFlatrate().get(i).getProviderName());
                responseFlatrateDTOList.add(response);
                i++;
            }
            responseJustWatchDTO.setFlatrate(responseFlatrateDTOList);
        }

        return responseJustWatchDTO;
    }

    private List<String> getMovieActors(Params params, Long movieId) {
        List<String> actorsList = new ArrayList<>();
        int actorListSize = 3;

        ResponseApiMovieCreditsDTO movieCredits = movieSearch.getMovieCredits(params, movieId);

        for (ResponseApiMovieCreditsCastDTO cast : movieCredits.getCast()) {
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

    private HashSet<ResponseApiUserDTO> buildResponseClientList(ResponseApiSearchByDTO apiSearchBy) {
        Params params = new Params(apiKey);
        HashSet<ResponseApiUserDTO> responseApiUserDTOList = new HashSet<>();

        for (ResponseApiUserDTO response : responseApiUserDTOList) {
            int i = 0;
            ResponseApiResultDTO responseMovie = apiSearchBy.getResults().get(i);

            List<GenresEnum> genresEnumList = genresIdToGenresString(responseMovie.getGenreIds());
            List<String> actors = getMovieActors(params, responseMovie.getId());

            String yearRelease = getYearRelease(responseMovie);

            Double rentPrice = this.rentPriceService.getRentPriceFromYear(yearRelease);
            ResponseJustWatchDTO responseJustWatchDTO = getMovieJustWatch(responseMovie.getId(), rentPrice, params);

            response.setMovieId(responseMovie.getId());
            response.setTitle(responseMovie.getTitle());
            response.setGenrers(genresEnumList);
            response.setReleaseYear(yearRelease);
            response.setActors(actors);
            response.setOverview(responseMovie.getOverview());
            response.setPoster(responseMovie.getPosterPath());
            response.setJustWatch(responseJustWatchDTO);

            responseApiUserDTOList.add(response);
            i++;
        }
        return responseApiUserDTOList;
    }

    private String getYearRelease(ResponseApiResultDTO responseApiResultDTO) {
        String yearRelease = "2020";
        if (responseApiResultDTO.getReleaseDate() != null && !responseApiResultDTO.getReleaseDate().isBlank()) {
            yearRelease = responseApiResultDTO.getReleaseDate().substring(0, 4);
        }
        return yearRelease;
    }

    private List<GenresEnum> genresIdToGenresString(List<Long> genresIds) {
        List<GenresEnum> genresEnumList = new ArrayList<>();
        for (Long genresId : genresIds) {
            GenresEnum genresEnum = GenresEnum.valueOfId(genresId);
            genresEnumList.add(genresEnum);
        }
        return genresEnumList;
    }

    public List<Long> actorsStringToActorsId (List<String> actors) {
        List<Long> actorsId = new ArrayList<>();
        for (String actor : actors) {
            ResponseApiSearchByActorDTO moviesByActors = movieSearch.getMoviesByActors(new ParamsSearchByName(apiKey, actor));
            List<ResponseApiResultActorDTO> results = moviesByActors.getResults();

            for (ResponseApiResultActorDTO result : results) {
                boolean acting = result.getKnownForDepartment().equals("Acting");
                if (acting){
                    actorsId.add(result.getId());
                }
            }
        }
        return actorsId;
    }

    public ResponseApiUserMovieByIdDTO getMovieById(Params params, Long id) {
        try {
            ResponseApiResultDTO movieById = movieSearch.getMovieById(params, id);

            ResponseApiUserMovieByIdDTO responseApiUserMovieByIdDTO = new ResponseApiUserMovieByIdDTO();
            String yearRelease = getYearRelease(movieById);
            Double rentPrice = this.rentPriceService.getRentPriceFromYear(yearRelease);

            responseApiUserMovieByIdDTO.setId(movieById.getId());
            responseApiUserMovieByIdDTO.setMovieName(movieById.getTitle());

            ResponseJustWatchDTO movieJustWatch = getMovieJustWatch(movieById.getId(), rentPrice, params);
            responseApiUserMovieByIdDTO.setJustWatch(movieJustWatch);

            return responseApiUserMovieByIdDTO;
        }catch (FeignException.FeignClientException.NotFound exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
