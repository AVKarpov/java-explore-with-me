package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.user.dto.UserShortDto;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class EventShortDto {

    /**
     * Идентификатор события
     */
    private Long id;

    /**
     * Заголовок события (от 3 до 120 символов)
     */
    private String title;

    /**
     * Краткое описание события (от 20 до 2000 символов)
     */
    private String annotation;

    /**
     * Категория к которой относится событие
     */
    private CategoryDto category;

    /**
     * Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
     */
    private String eventDate;

    /**
     * Количество одобренных заявок на участие в данном событии
     */
    private long confirmedRequests;

    /**
     * Пользователь (краткая информация)
     */
    private UserShortDto initiator;

    /**
     * Нужно ли оплачивать участие в событии
     */
    private boolean paid;

    /**
     * Количество просмотрев события
     */
    private long views;

}
