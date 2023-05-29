package ru.practicum.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Stat {

    /**
     * Название сервиса
     */
    private String app;

    /**
     * URI сервиса
     */
    private String uri;

    /**
     * Количество просмотров
     */
    private Long hits;

    public Stat(String app, String uri, Long hits) {
        this.app = app;
        this.uri = uri;
        this.hits = hits;
    }
}