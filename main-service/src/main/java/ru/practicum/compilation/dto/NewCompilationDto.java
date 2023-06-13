package ru.practicum.compilation.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Подборка событий
 */
@Data
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class NewCompilationDto {

    /**
     * Список идентификаторов событий входящих в подборку
     */
    private List<Long> events;

    /**
     * Закреплена ли подборка на главной странице сайта (по умолчанию false)
     */
    private Boolean pinned = false;

    /**
     * Заголовок подборки
     */
    @NotNull @NotBlank @Size(min = 1, max = 50)
    private String title;

}
