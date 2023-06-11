package ru.practicum.event.dto;

import lombok.*;
import ru.practicum.event.StateUserAction;
import ru.practicum.location.dto.LocationDto;

import javax.validation.constraints.Size;

/**
 * Данные для изменения информации о событии.
 * Если поле в запросе не указано (равно null) - значит изменение этих данных не требуется.
 */
@Data
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdateEventUserRequestDto {

    /**
     * Заголовок события (от 3 до 120 символов)
     */
    @Size(min = 3, max = 120)
    private String title;

    /**
     * Краткое описание события (от 20 до 2000 символов)
     */
    @Size(min = 20, max = 2000)
    private String annotation;

    /**
     * Категория к которой относится событие
     */
    private Long category;

    /**
     * Полное описание события (от 20 до 7000 символов)
     */
    @Size(min = 20, max = 7000)
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
    private Boolean paid;

    /**
     * Ограничение на количество участников. Значение 0 - означает отсутствие ограничения (по умолчанию 0)
     */
    private Integer participantLimit;

    /**
     * Нужна ли пре-модерация заявок на участие. Если true, то все заявки будут ожидать подтверждения инициатором события.
     * Если false - то будут подтверждаться автоматически.
     */
    private Boolean requestModeration;

    /**
     * Изменение состояния события (SEND_TO_REVIEW, CANCEL_REVIEW)
     */
    private StateUserAction stateAction;

}
