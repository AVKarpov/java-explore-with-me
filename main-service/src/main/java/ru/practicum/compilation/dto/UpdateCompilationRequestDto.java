package ru.practicum.compilation.dto;

import lombok.*;

import java.util.List;

/**
 * Изменение информации о подборке событий.
 * Если поле в запросе не указано (равно null) - значит изменение этих данных не треубется.
 */
@Data
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdateCompilationRequestDto {

    /**
     * Список идентификаторов событий входящих в подборку для полной замены текущего списка
     */
    private List<Long> events;

    /**
     * Закреплена ли подборка на главной странице сайта
     */
    private Boolean pinned;

    /**
     * Заголовок подборки
     */
    private String title;

}
