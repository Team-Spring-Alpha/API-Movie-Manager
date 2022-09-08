package br.com.compass.search.dto.apiclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseApiClientMovieById {
    @ApiModelProperty(position = 1)
    private Long id;
    @ApiModelProperty(position = 2)
    @JsonProperty("movie_name")
    private String movieName;
    @ApiModelProperty(position = 3)
    @JsonProperty("just_watch")
    private ResponseJustWatch justWatch;
}
