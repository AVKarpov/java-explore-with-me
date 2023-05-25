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

    private String app; //название сервиса

    private String uri; //URI сервиса

    private int hits; //Количество просмотров
}