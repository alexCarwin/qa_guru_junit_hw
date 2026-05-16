package ru.solnyshko.data;

public enum Language {
    RU("Википедия"),
    EN("Wikipedia");


    public final String description;

    Language(String description){
        this.description = description;
    }

}
