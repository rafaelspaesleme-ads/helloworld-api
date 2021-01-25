package com.hello.world.utils.functions;

import com.hello.world.utils.enums.NativeLangEnum;
import org.springframework.stereotype.Component;

@Component
public class ConvertionsFunction {
    public static NativeLangEnum convertStringToNLE(String nativeLang) {
        if (nativeLang != null) {
            String langClean = nativeLang.toUpperCase();
            String lang = langClean
                    .replace("Ñ", "N")
                    .replace("Ç", "C")
                    .replace("Ê", "E");

            if (NativeLangEnum.values().equals(lang)) {
                return NativeLangEnum.valueOf(lang);
            } else {
                return NativeLangEnum.PORTUGUES;
            }

        } else {
            return NativeLangEnum.PORTUGUES;
        }
    }

    public static String convertionNameUSA(String usa) {
        if (usa != null) {
            switch (usa) {
                case "Vereinigte Staaten von Amerika":
                case "Vereinigte Staaten":
                    return "Vereinigte Staaten";
                case "Etats-Unis":
                case "les États-Unis d'Amérique":
                    return "États Unis";
                case "EUA":
                case "Estados Unidos":
                case "Estados Unidos de América":
                case "Estados Unidos da America":
                    return "Estados Unidos";
                case "USA":
                case "United States of America":
                    return "United States";
                default:
                    return usa;
            }
        } else {
            return null;
        }
    }
}
