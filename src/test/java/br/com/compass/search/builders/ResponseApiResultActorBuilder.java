package br.com.compass.search.builders;

import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResultActorDTO;

public class ResponseApiResultActorBuilder {

    private ResponseApiResultActorDTO responseApiResultActorDTO;

    public ResponseApiResultActorBuilder() {
    }

    public static ResponseApiResultActorBuilder one() {
        ResponseApiResultActorBuilder builder = new ResponseApiResultActorBuilder();
        builder.responseApiResultActorDTO = new ResponseApiResultActorDTO();

        builder.responseApiResultActorDTO.setAdult(false);
        builder.responseApiResultActorDTO.setGender(0);
        builder.responseApiResultActorDTO.setId(1L);
        builder.responseApiResultActorDTO.setResults(null);
        builder.responseApiResultActorDTO.setKnownForDepartment("Acting");
        builder.responseApiResultActorDTO.setName("test");
        builder.responseApiResultActorDTO.setPopularity(5.0);
        builder.responseApiResultActorDTO.setProfilePath("test url");

        return builder;
    }

    public ResponseApiResultActorBuilder withId(Long id) {
        this.responseApiResultActorDTO.setId(id);
        return this;
    }

    public ResponseApiResultActorBuilder withKnownForDepartment(String knownForDepartment) {
        this.responseApiResultActorDTO.setKnownForDepartment(knownForDepartment);
        return this;
    }

    public ResponseApiResultActorDTO now() {
        return this.responseApiResultActorDTO;
    }

}
