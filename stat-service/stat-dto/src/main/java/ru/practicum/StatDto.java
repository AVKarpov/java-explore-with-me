package ru.practicum;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
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

    @Override
    public String toString() {
        return "StatDto{" +
                "app='" + app + '\'' +
                ", uri='" + uri + '\'' +
                ", hits=" + hits +
                '}';
    }
}