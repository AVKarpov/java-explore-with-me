package ru.practicum.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.event.dto.EventShortDto;

import java.util.List;

/**
 * Подборка событий
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CompilationDto {

    /**
     * Идентификатор подборки
     */
    private Long id;

    /**
     * Список событий входящих в подборку
     */
    private List<EventShortDto> events;

    /**
     * Закреплена ли подборка на главной странице сайта
     */
    private Boolean pinned;

    /**
     * Заголовок подборки
     */
    private String title;

}
