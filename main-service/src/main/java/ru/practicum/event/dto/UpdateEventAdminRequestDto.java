package ru.practicum.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.practicum.event.StateAdminAction;
import ru.practicum.location.dto.LocationDto;

import javax.validation.constraints.Size;

/**
 * Данные для изменения информации о событии.
 * Если поле в запросе не указано (равно null) - значит изменение этих данных не требуется.
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UpdateEventAdminRequestDto {

    /**
     * Новый заголовок события (от 3 до 120 символов)
     */
    @Size(min = 3, max = 120)
    private String title;

    /**
     * Новое краткое описание события (от 20 до 2000 символов)
     */
    @Size(min = 20, max = 2000)
    private String annotation;

    /**
     * Новая категория к которой относится событие
     */
    private Long category;

    /**
     * Новое полное описание события (от 20 до 7000 символов)
     */
    @Size(min = 20, max = 7000)
    private String description;

    /**
     * Новые дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
     */
    private String eventDate;

    /**
     * Широта и долгота места проведения события
     */
    private LocationDto location;

    /**
     * Новое значение флага о платности мероприятия
     */
    private Boolean paid;

    /**
     * Новый лимит пользователей
     */
    private Integer participantLimit;

    /**
     * Нужна ли пре-модерация заявок на участие. Если true, то все заявки будут ожидать подтверждения инициатором события.
     * Если false - то будут подтверждаться автоматически.
     */
    private Boolean requestModeration;

    /**
     * Новое состояние события (PUBLISH_EVENT, REJECT_EVENT)
     */
    private StateAdminAction stateAction;

}
