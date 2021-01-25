package com.hello.world.services.builders;

import com.hello.world.domain.entities.Translate;
import com.hello.world.services.dtos.TranslateDTO;
import com.hello.world.utils.enums.LanguagesEnum;
import org.springframework.stereotype.Component;

import static com.hello.world.utils.functions.TranslateFunction.*;

@Component
public class TranslateBuilder {
    public static TranslateDTO buildTranslateDTO(Translate translate) {
        return TranslateDTO.builder()
                .withId(translate.getId())
                .withLanguage(translate.getLanguage())
                .withNationInput(translate.getNationInput())
                .withNationOutput(translate.getNationOutput())
                .withStatus(translate.getStatus())
                .build();
    }

    public static String buildNationTranslate(TranslateDTO translate) {
        return translate.getNationInput() + " é falado " + translate.getNationOutput() + " em " + convertNameTranslate(LanguagesEnum.valueOf(translate.getLanguage().toUpperCase()))  + ".";
    }
}
