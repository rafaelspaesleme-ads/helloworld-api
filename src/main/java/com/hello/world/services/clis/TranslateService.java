package com.hello.world.services.clis;

import com.hello.world.services.dtos.TranslateDTO;
import com.hello.world.utils.enums.LanguagesEnum;

import java.util.Optional;

public interface TranslateService {
    Optional<?> translator(LanguagesEnum nativeLang, String nation, LanguagesEnum langTranslateTo, Boolean status);
    Optional<TranslateDTO> translatorInternal(LanguagesEnum nativeLang, String nation, LanguagesEnum langTranslateTo, Boolean status);
}
