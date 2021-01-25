package com.hello.world.utils.functions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TemplatesFunction {

    @Value(value = "${access.base-url}")
    private String BASE_URL;

    private String URL_IMG = "data:image/png;base64,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";

    private final String DESCRIPTION_API = "Uma API para acesso a todos os países, estados, regiões e cidades de todo o mundo, com buscas por códigos postais. (Tradução para cinco países).";

    public String initalPageHtml() {
        try {
            return "<html>" +
                    "<body>" +
                    "<div style='padding=50px;text-align=justify;font-family: Tahoma, sans-serif;'>" +
                    "<h1>" +
                    "Bem vindo ao Hello World API - 1.0.0-beta" +
                    "</h1>" +
                    "<br/>" +
                    "<p>" + DESCRIPTION_API + "</p>" +
                    "<br/>" +
                    "<strong>Para ter acesso a nossa documentação de endpoints do swagger, <a href='" + BASE_URL + "/swagger-ui.html'>clique aqui</a></strong>" +
                    checkAccessH2(BASE_URL) +
                    "<br/>" +
                    "<br/>" +
                    "<hr/>" +
                    "<br/>" +
                    "<br/>" +
                    "<center>" +
                    "<img src='" + URL_IMG + "' alt='logo'/>" +
                    "<br/>" +
                    "<br/>" +
                    "<hr/>" +
                    "<br/>" +
                    "<br/>" +
                    "</center>" +
                    "</div>" +
                    "</body>" +
                    "<script src=\"https://gist.github.com/rafaelspaesleme-ads/c88cc2b87291572ce249ab94b183b733.js\"></script>" +
                    "</html>";

        } catch (Exception e) {
            return null;
        }
    }

    private String checkAccessH2(String baseUrl) {
        return baseUrl.contains("localhost") ? "<br/>-<br/><strong>Para ter acesso ao banco de dados embarcado, <a href='" + baseUrl + "/h2-console'>clique aqui</a></strong>" : "";
    }
}
