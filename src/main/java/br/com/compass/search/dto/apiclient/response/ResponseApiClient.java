package br.com.compass.search.dto.apiclient.response;

import br.com.compass.search.enums.GenresEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode
public class ResponseApiClient {
    @ApiModelProperty(position = 1)
    @JsonProperty("movie_id")
    private Long movieId;
    @ApiModelProperty(position = 2)
    private String title;
    @ApiModelProperty(position = 3)
    private List<GenresEnum> genrers;
    @ApiModelProperty(position = 4, example = "2015")
    @JsonProperty("release_year")
    private String releaseYear;
    @ApiModelProperty(position = 5)
    private List<String> actors;
    @ApiModelProperty(position = 6)
    private String overview;
    @ApiModelProperty(position = 7)
    private String poster;
    @ApiModelProperty(position = 8)
    @JsonProperty("just_watch")
    private ResponseJustWatch justWatch;
}
