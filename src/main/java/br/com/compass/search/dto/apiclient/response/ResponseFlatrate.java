package br.com.compass.search.dto.apiclient.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseFlatrate {
    @JsonProperty("provider_name")
    private String providerName;
}
