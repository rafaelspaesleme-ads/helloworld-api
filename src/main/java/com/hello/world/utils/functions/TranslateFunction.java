package com.hello.world.utils.functions;

import com.hello.world.utils.enums.ApiEnum;
import com.hello.world.utils.enums.LanguagesEnum;
import com.hello.world.utils.enums.NativeLangEnum;

import static com.hello.world.utils.enums.NativeLangEnum.*;

public class TranslateFunction {
    public static String replaceResponseTranslate(String response) {
        return response
                .replace("[{\"format\":0,\"translations\":{", "{")
                .replace("[{\"format\":1,\"translations\":{", "{")
                .replace("[{\"format\":2,\"translations\":{", "{")
                .replace("[{\"format\":3,\"translations\":{", "{")
                .replace("{\"format\":0,\"translations\":{", "")
                .replace("{\"format\":1,\"translations\":{", "")
                .replace("{\"format\":2,\"translations\":{", "")
                .replace("{\"format\":3,\"translations\":{", "")
                .replace("}},", ",")
                .replace("]}}]", "]}")
                .replace("[", "")
                .replace("]", "")
                .replace("{", "")
                .replace("}", "");
    }

    public static String generatorJsonTranslate(String nativeLang, String nation) {
        return "{\"language\":\"" + nativeLang + "\",\"content\":\"" + nation + "\"}";
    }

    public static String replaceResponseTranslateMaster(String str) {
        String str1 = str.replace("\"en\":\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"Brazil\",\"USA\",\"USA\",\"USA\",", "\"en\":\"Brazil\",");
        String str2 = str1.replace("\"es\":\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"USA\",\"USA\",\"USA\",", "\"es\":\"Brasil\",");
        String str3 = str2.replace("\"fr\":\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"Bresil\",\"USA\",\"USA\",\"USA\",", "\"fr\":\"Bresil\",");
        return str3.replace("\"pt\":\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"Brasil\",\"USA\",\"USA\",\"USA\",", "\"pt\":\"Brasil\",");
    }

    public static String convertNameTranslate(LanguagesEnum languagesEnum) {
        switch (languagesEnum) {
            case EN:
                return "Inglês";
            case DE:
                return "Alemão";
            case ES:
                return "Espanhol";
            case FR:
                return "Francês";
            case PT:
                return "Portugês";
            default:
                return "Linguagem não identificada ou cadastrada em nossa base de dados.";
        }
    }

    public static String translatorContinentToPtBr(String continent) {
        switch (continent) {
            case "Africa":
                return "África";
            case "Antarctica":
                return "Antártida";
            case "Asia":
                return "Ásia";
            case "Europe":
                return "Europa";
            case "North America":
            case "Northern America":
                return "América do Norte";
            case "Central America":
                return "América Central";
            case "Oceania":
                return "Oceania";
            case "South America":
                return "América do Sul";
            default:
                return "Não existe";
        }
    }

    public static String translatorContinentNativeLangToEng(String continent, ApiEnum api, NativeLangEnum nativeLangEnum) {
        if (continent != null) {
            switch (nativeLangEnum) {
                case ENGLISH:
                    return continent;
                case ESPANOL:
                    return translatorContinentESToEng(continent.toUpperCase(), api);
                case DEUTSCHE:
                    return translatorContinentDEToEng(continent.toUpperCase(), api);
                case FRANCAIS:
                    return translatorContinentFRToEng(continent.toUpperCase(), api);
                case PORTUGUES:
                    return translatorContinentPTBrToEng(continent.toUpperCase(), api);
                default:
                    return "Não existe";
            }
        } else {
            return "Não existe";
        }

    }

    public static String translatorContinentFRToEng(String continentNormal, ApiEnum api) {

        String continent = continentNormal.toUpperCase();

        if ("AFRIQUE".equals(continent) || "AFRICA".equals(continent)) {
            return "Africa";
        } else if ("ANTARCTIQUE".equals(continent) || "ANTARCTICA".equals(continent)) {
            return "Antarctica";
        } else if ("ASIE".equals(continent)) {
            return "Asia";
        } else if ("EUROPE".contains(continent)) {
            return "Europe";
        } else if ("AMÉRIQUE DU NORD".equals(continent) || "AMERIQUE DU NORD".equals(continent) || "NORTHERN AMERICA".equals(continent) || "NORTH AMERICA".equals(continent)) {
            if (api.equals(ApiEnum.COUNTRY)) {
                return "Northern America";
            } else {
                return "North America";
            }
        } else if ("AMÉRIQUE CENTRALE".equals(continent) || "AMERIQUE CENTRALE".equals(continent) || "CENTRAL AMERICA".equals(continent)) {
            return "Central America";
        } else if ("OCÉANIE".equals(continent) || "OCEANIE".equals(continent) || "OCEANIA".equals(continent)) {
            return "Oceania";
        } else if ("AMÉRIQUE DU SUD".equals(continent) || "AMERIQUE DU SUD".equals(continent) || "SOUTH AMERICA".equals(continent)) {
            return "South America";
        } else if ("AMÉRIQUE".equals(continent) || "AMERIQUE".equals(continent) || "AMERICAS".equals(continent) || "AMÉRICAS".equals(continent)) {
            return "Americas";
        }
        return "Não existe";
    }

    public static String translatorContinentESToEng(String continentNormal, ApiEnum api) {

        String continent = continentNormal.toUpperCase();

        if ("ÁFRICA".equals(continent) || "AFRICA".equals(continent)) {
            return "Africa";
        } else if ("ANTÁRTIDA".equals(continent) || "ANTARTIDA".equals(continent) || "ANTARCTICA".equals(continent)) {
            return "Antarctica";
        } else if ("ASIA".equals(continent)) {
            return "Asia";
        } else if ("EUROPA".equals(continent) || "EUROPE".equals(continent) ) {
            return "Europe";
        } else if ("AMÉRICA DEL NORTE".equals(continent) || "AMERICA DEL NORTE".equals(continent) || "NORTHERN AMERICA".equals(continent) || "NORTH AMERICA".equals(continent)) {
            if (api.equals(ApiEnum.COUNTRY)) {
                return "Northern America";
            } else {
                return "North America";
            }
        } else if ("AMÉRICA CENTRAL".equals(continent) || "AMERICA CENTRAL".equals(continent) || "CENTRAL AMERICA".equals(continent)) {
            return "Central America";
        } else if ("OCEANÍA".equals(continent) || "OCEANIA".equals(continent)) {
            return "Oceania";
        } else if ("AMÉRICA DEL SUR".equals(continent) || "AMERICA DEL SUR".equals(continent) || "SOUTH AMERICA".equals(continent)) {
            return "South America";
        } else if ("AMERICA".equals(continent) || "AMERICAS".equals(continent) || "AMÉRICAS".equals(continent)) {
            return "Americas";
        }
        return "Não existe";
    }

    public static String translatorContinentDEToEng(String continentNormal, ApiEnum api) {

        String continent = continentNormal.toUpperCase();

        if ("AFRIKA".equals(continent) || "AFRICA".equals(continent)) {
            return "Africa";
        } else if ("ANTARKTIE".equals(continent) || "ANTARCTICA".equals(continent)) {
            return "Antarctica";
        } else if ("ASIEN".equals(continent) || "ASIA".equals(continent)) {
            return "Asia";
        } else if ("EUROPA".equals(continent) || "EUROPE".equals(continent)) {
            return "Europe";
        } else if ("NORDAMERIKA".equals(continent) || "NORTHERN AMERICA".equals(continent) || "NORTH AMERICA".equals(continent)) {
            if (api.equals(ApiEnum.COUNTRY)) {
                return "Northern America";
            } else {
                return "North America";
            }
        } else if ("ZENTRALAMERIKA".equals(continent) || "CENTRAL AMERICA".equals(continent)) {
            return "Central America";
        } else if ("OZEANIEN".equals(continent) || "OCEANIA".equals(continent)) {
            return "Oceania";
        } else if ("SÜDAMERIKA".equals(continent) || "SUDAMERIKA".equals(continent) || "SOUTH AMERICA".equals(continent)) {
            return "South America";
        } else if ("AMERIKA".equals(continent) || "AMERICAS".equals(continent) || "AMÉRICAS".equals(continent)) {
            return "Americas";
        }
        return "Não existe";
    }

    public static String translatorContinentPTBrToEng(String continentNormal, ApiEnum api) {

        String continent = continentNormal.toUpperCase();

        if ("ÁFRICA".equals(continent) || "AFRICA".equals(continent)) {
            return "Africa";
        } else if ("ANTÁRTIDA".equals(continent) || "ANTARTIDA".equals(continent) || "ANTARCTICA".equals(continent)) {
            return "Antarctica";
        } else if ("ÁSIA".equals(continent) || "ASIA".equals(continent)) {
            return "Asia";
        } else if ("EUROPA".equals(continent) || "EUROPE".equals(continent)) {
            return "Europe";
        } else if ("AMÉRICA DO NORTE".equals(continent) || "AMERICA DO NORTE".equals(continent) || "NORTHERN AMERICA".equals(continent) || "NORTH AMERICA".equals(continent)) {
            if (api.equals(ApiEnum.COUNTRY)) {
                return "Northern America";
            } else {
                return "North America";
            }
        } else if ("AMÉRICA CENTRAL".equals(continent) || "AMERICA CENTRAL".equals(continent) || "CENTRAL AMERICA".equals(continent)) {
            return "Central America";
        } else if ("OCEANIA".equals(continent)) {
            return "Oceania";
        } else if ("AMÉRICA DO SUL".equals(continent) || "AMERICA DO SUL".equals(continent) || "SOUTH AMERICA".equals(continent)) {
            return "South America";
        } else if ("AMÉRICA".equals(continent) || "AMERICAS".equals(continent) || "AMÉRICAS".equals(continent)) {
            return "Americas";
        }
        return "Não existe";
    }

    public static LanguagesEnum convertNativeLang(String nativeLang) {
        String langClean = nativeLang
                .replace("ñ", "n")
                .replace("ç", "c")
                .replace("ê", "e");
        String lang = langClean.toUpperCase();

        switch (NativeLangEnum.valueOf(lang)) {
            case ENGLISH:
                return LanguagesEnum.EN;
            case DEUTSCHE:
                return LanguagesEnum.DE;
            case ESPANOL:
                return LanguagesEnum.ES;
            case FRANCAIS:
                return LanguagesEnum.FR;
            case PORTUGUES:
                return LanguagesEnum.PT;
            default:
                return null;
        }
    }

    public static String translatePlanet(String earth) {

        String s = earth.toUpperCase();

        switch (s) {
            case "EARTH":
            case "ERDE":
            case "TIERRA":
            case "TERRE":
            case "TERRA":
                return "Terra";
            default:
                return "Error";
        }
    }
}
