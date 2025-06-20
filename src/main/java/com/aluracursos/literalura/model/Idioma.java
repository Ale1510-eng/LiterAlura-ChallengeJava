package com.aluracursos.literalura.model;

public enum Idioma {
    en("en", "ingles"),
    es("es", "espanol");

    private String idiomasGundex;
    private String idiomaEspanol;

    Idioma(String idiomasGundex, String idiomaEspanol){
        this.idiomasGundex = idiomasGundex;
        this.idiomaEspanol = idiomaEspanol;
    }

    public static Idioma fromString(String text){
        for (Idioma idioma: Idioma.values()){
            if (idioma.idiomasGundex.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningún idioma encontrado: " + text);
    }

    public static Idioma fromEspanol(String text){
        for(Idioma idioma : Idioma.values()){
            if(idioma.idiomaEspanol.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        throw new IllegalArgumentException("Nigun idioma encontrado: " + text);
    }


}

