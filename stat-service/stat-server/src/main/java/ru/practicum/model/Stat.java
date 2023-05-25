package ru.practicum.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stat {

    private String app; //название сервиса

    private String uri; //URI сервиса

    private Long hits; //Количество просмотров

    public Stat(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}