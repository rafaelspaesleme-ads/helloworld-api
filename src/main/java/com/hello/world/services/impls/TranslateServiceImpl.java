package com.hello.world.services.impls;

import com.hello.world.domain.entities.Translate;
import com.hello.world.domain.persistances.daos.TranslateDAO;
import com.hello.world.services.builders.TranslateBuilder;
import com.hello.world.services.clis.TranslateService;
import com.hello.world.services.dtos.TranslateDTO;
import com.hello.world.utils.enums.LanguagesEnum;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static com.hello.world.services.builders.TranslateBuilder.*;
import static com.hello.world.utils.functions.TranslateFunction.*;

@Service
public class TranslateServiceImpl implements TranslateService {

    private static final String COMMA = ",";
    private static final String TWO_POINTS = ":";
    private static final String QUOTATION_MARKS = "\"";
    private static final String EMPTY = "";

    @Value(value = "${translate.endpoint}")
    private String URL_ENDPOINT;
    @Value(value = "${translate.content-type.key}")
    private String CONTENT_TYPE_KEY;
    @Value(value = "${translate.content-type.value}")
    private String CONTENT_TYPE_VALUE;
    @Value(value = "${translate.token.key}")
    private String TOKEN_KEY;
    @Value(value = "${translate.token.value}")
    private String TOKEN_VALUE;


    private final TranslateDAO translateDAO;

    private TranslateServiceImpl(TranslateDAO translateDAO) {
        this.translateDAO = translateDAO;
    }

    @Override
    public Optional<?> translator(LanguagesEnum nativeLang, String nation, LanguagesEnum langTranslateTo, Boolean status) {
        Optional<TranslateDTO> translate = getTranslate(nativeLang, nation, langTranslateTo, status);
        if (translate.isPresent()) {
            return Optional.of(buildNationTranslate(translate.get()));
        } else {
            return Optional.empty();
        }

    }

    @Override
    public Optional<TranslateDTO> translatorInternal(LanguagesEnum nativeLang, String nation, LanguagesEnum langTranslateTo, Boolean status) {
        Optional<TranslateDTO> translate = getTranslate(nativeLang, nation, langTranslateTo, status);
        if (translate.isPresent()) {
            return translate;
        } else {
            return Optional.empty();
        }

    }

    public Optional<TranslateDTO> getTranslate(LanguagesEnum nativeLang, String nation, LanguagesEnum langTranslateTo, Boolean status) {
        try {
            List<Translate> translates = new ArrayList<>();

            String url = URL_ENDPOINT;
            HttpPost httpPost = new HttpPost(url);

            String json = generatorJsonTranslate(nativeLang.name().toLowerCase(), nation);
            StringEntity e;
            e = new StringEntity(json, Consts.UTF_8);
            e.setContentType(CONTENT_TYPE_VALUE);
            e.getContent();

            httpPost.addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
            httpPost.setHeader(TOKEN_KEY, TOKEN_VALUE);
            httpPost.setEntity(e);

            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);
            response.setHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE);
            HttpEntity entity = response.getEntity();
            entity.getContentEncoding();
            entity.getContentType();
            String result = EntityUtils.toString(entity);

            String str = replaceResponseTranslate(result);

            String[] pacote1;

            if (str.contains("\"" + nativeLang.name().toLowerCase() + "\":\"" + nation + "\",\"" + nation + "\",\"" + nation + "\"")) {
                String strFilter = replaceResponseTranslateMaster(str);
                pacote1 = strFilter.split(COMMA);
            } else {
                pacote1 = str.split(COMMA);
            }


            List<String> key = new ArrayList<>();

            List<String> value = new ArrayList<>();

            for (String s : pacote1) {
                String[] pacote2 = s.split(TWO_POINTS);
                for (int i = 0; i < pacote2.length; i++) {
                    if (i % 2 == 0) {
                        key.add(pacote2[i]);
                    } else {
                        value.add(pacote2[i]);
                    }
                }
            }

            IntStream.range(0, key.size()).forEach(index -> {
                translates.add(translateDAO.saveOrUpdate(Translate
                        .builder()
                        .withLanguage(key.get(index).replace(QUOTATION_MARKS, EMPTY).toUpperCase())
                        .withNationOutput(value.get(index).replace(QUOTATION_MARKS, EMPTY))
                        .withNationInput(nation)
                        .withStatus(status)
                        .build()).get());
            });

            Optional<Translate> nationMultiLang = translates.stream()
                    .filter(nml -> nml.getLanguage().equals(langTranslateTo.name()))
                    .findFirst();

            return nationMultiLang.map(TranslateBuilder::buildTranslateDTO);

        } catch (IOException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }


}
