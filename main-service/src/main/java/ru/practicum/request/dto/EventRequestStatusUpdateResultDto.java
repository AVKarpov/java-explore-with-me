package ru.practicum.request.dto;

import lombok.*;

import java.util.List;

/**
 * Результат подтверждения/отклонения заявок на участие в событии
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class EventRequestStatusUpdateResultDto {

    /**
     * Список подтвержденных заявок на участие в событии
     */
    private List<ParticipationRequestDto> confirmedRequests;

    /**
     * Новый статус запроса на участие в событии текущего пользователя (CONFIRMED, REJECTED)
     */
    private List<ParticipationRequestDto> rejectedRequests;

}
