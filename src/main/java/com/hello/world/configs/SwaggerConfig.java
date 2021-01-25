package com.hello.world.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String NAME_API = "Hello World API";
    private static final String DESCRIPTION_API = "Uma API para acesso a todos os países, estados, regiões e cidades de todo o mundo, com buscas por códigos postais. (Tradução para cinco países).";
    @Value(value = "${app.version.api}")
    private String VERSION_API;
    @Value(value = "${app.license.api}")
    private String LICENSE_API;
    @Value(value = "${app.url-license.api}")
    private String LICENSE_URL_API;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hello.world.resources.v1.controllers"))
                .paths(PathSelectors.any())
                .build()
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(NAME_API)
                .description(DESCRIPTION_API)
                .version(VERSION_API)
                .license(LICENSE_API)
                .contact(new Contact("Base URL para o seu projeto Frontend", "https://rpl-helloworld-api.herokuapp.com/world", "rafaelspaesleme.ads@gmail.com"))
                .licenseUrl(LICENSE_URL_API)
                        .contact(new Contact("Rafael Paes Leme", "https://github.com/rafaelspaesleme-ads", "rafaelspaesleme.ads@gmail.com"))
                        .build();
    }

    private List<ResponseMessage> responseMessageForGET()
    {
        return new ArrayList<ResponseMessage>() {{
            add(new ResponseMessageBuilder()
                    .code(500)
                    .message("Não foi possivel obter conexão com um de nossos servidores.")
                    .responseModel(new ModelRef("Error"))
                    .build());
            add(new ResponseMessageBuilder()
                    .code(200)
                    .message("Dados foram retornados com sucesso!")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(501)
                    .message("O servidor não aceitou implementar os dados enviados nessa requisição.")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(503)
                    .message("Um ou mais servidores não estão prontos para lidar com esta requisição.")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(404)
                    .message("Dados do endpoint da requisição invalidos ou não existe.")
                    .build());
        }};
    }

    private List<ResponseMessage> responseMessageForPOST()
    {
        return new ArrayList<ResponseMessage>() {{
            add(new ResponseMessageBuilder()
                    .code(500)
                    .message("Não foi possivel obter conexão com um de nossos servidores.")
                    .responseModel(new ModelRef("Error"))
                    .build());
            add(new ResponseMessageBuilder()
                    .code(201)
                    .message("Tradução cadastrada com sucesso.")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(503)
                    .message("Um ou mais servidores não estão prontos para lidar com esta requisição.")
                    .build());
            add(new ResponseMessageBuilder()
                    .code(404)
                    .message("Dados do endpoint da requisição invalidos ou não existe.")
                    .build());
        }};
    }
}
