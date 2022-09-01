package br.com.compass.search.builders;

import br.com.compass.search.dto.apiTheMoviedb.ResponseApiResultActor;

public class ResponseApiResultActorBuilder {

    private ResponseApiResultActor responseApiResultActor;

    public ResponseApiResultActorBuilder() {
    }

    public static ResponseApiResultActorBuilder one() {
        ResponseApiResultActorBuilder builder = new ResponseApiResultActorBuilder();
        builder.responseApiResultActor = new ResponseApiResultActor();

        builder.responseApiResultActor.setAdult(false);
        builder.responseApiResultActor.setGender(0);
        builder.responseApiResultActor.setId(1L);
        builder.responseApiResultActor.setResults(null);
        builder.responseApiResultActor.setKnownForDepartment("Acting");
        builder.responseApiResultActor.setName("test");
        builder.responseApiResultActor.setPopularity(5.0);
        builder.responseApiResultActor.setProfilePath("test url");

        return builder;
    }

    public ResponseApiResultActorBuilder withId(Long id) {
        this.responseApiResultActor.setId(id);
        return this;
    }

    public ResponseApiResultActorBuilder withKnownForDepartment(String knownForDepartment) {
        this.responseApiResultActor.setKnownForDepartment(knownForDepartment);
        return this;
    }

    public ResponseApiResultActor now() {
        return this.responseApiResultActor;
    }

}
