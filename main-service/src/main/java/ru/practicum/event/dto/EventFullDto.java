package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.location.dto.LocationDto;
import ru.practicum.user.dto.UserShortDto;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class EventFullDto {

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
     * Полное описание события (от 20 до 7000 символов)
     */
    private String description;

    /**
     * Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
     */
    private String eventDate;

    /**
     * Широта и долгота места проведения события
     */
    private LocationDto location;

    /**
     * Нужно ли оплачивать участие в событии
     */
    private boolean paid;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения (по умолчанию 0)
     */
    private int participantLimit;

    /**
     * Нужна ли пре-модерация заявок на участие. Если true, то все заявки будут ожидать подтверждения инициатором события.
     * Если false - то будут подтверждаться автоматически.
     */
    private boolean requestModeration;

    /**
     * Количество одобренных заявок на участие в данном событии
     */
    private long confirmedRequests;

    /**
     * Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
     */
    private String createdOn;

    /**
     * Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
     */
    private String publishedOn;

    /**
     * Пользователь (краткая информация)
     */
    private UserShortDto initiator;

    /**
     * Список состояний жизненного цикла события (PENDING, PUBLISHED, CANCELED)
     */
    private String state;

    /**
     * Количество просмотрев события
     */
    private long views;

}
