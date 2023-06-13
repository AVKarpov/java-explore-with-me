package ru.practicum.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Заявка на участие в событии
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ParticipationRequestDto {

    /**
     * Идентификатор заявки
     */
    private Long id;

    /**
     * Идентификатор события
     */
    private Long event;

    /**
     * Идентификатор пользователя, отправившего заявку
     */
    private Long requester;

    /**
     * Статус заявки
     */
    private String status;

    /**
     * Дата и время создания заявки
     */
    private String created;

}
