package ru.practicum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class StatDto {

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
    private int hits;
}