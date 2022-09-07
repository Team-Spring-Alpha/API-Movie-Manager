package br.com.compass.search.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class ConfigSpringFox {

    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                    .apis(RequestHandlerSelectors.basePackage("br.com.compass.search.controller"))
                    .paths(PathSelectors.regex("/api/movie-manager.*"))
                    .build()
                .apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("Api to search movies")
                .description("Api to search movies from The Movie DB")
                .version("1.0.0")
                .contact(new Contact("Team Spring Beta", "https://github.com/Team-Spring-Alpha", "team.spring.compass.alpha@gmail.com"))
                .license("MIT license")
                .licenseUrl("https://spdx.org/licenses/MIT.html")
                .build();
    }

}
