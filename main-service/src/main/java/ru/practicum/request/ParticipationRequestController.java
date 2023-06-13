package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.request.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ParticipationRequestController {

    private final ParticipationRequestService participationRequestService;

    /**
     * Добавление запроса от текущего пользователя на участие в событии
     * @param userId id текущего пользователя
     * @param eventId id события
     * @return ParticipationRequestDto Заявка на участие в событии, либо ошибка 400/404/409
     */
    @PostMapping("/users/{userId}/requests")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto createParticipationRequest(@PathVariable @Valid @Positive Long userId,
                                                              @RequestParam @Valid @Positive Long eventId) {
       return participationRequestService.createParticipationRequest(userId, eventId);
    }

    /**
     * Отмена своего запроса на участие в событии
     * @param userId id текущего пользователя
     * @param requestId id запроса на участие
     * @return ParticipationRequestDto Заявка на участие в событии, либо ошибка 404
     */
    @PatchMapping("/users/{userId}/requests/{requestId}/cancel")
    public ParticipationRequestDto cancelParticipationRequest(@PathVariable @Valid @Positive Long userId,
                                                              @PathVariable @Valid @Positive Long requestId) {
        return participationRequestService.cancelParticipationRequest(userId, requestId);
    }

    /**
     * Получение информации о заявках текущего пользователя на участие в чужих событиях
     * @param userId id текущего пользователя
     * @return List<ParticipationRequestDto> Запросы на участие, либо ошибки 400/404
     */
    @GetMapping("/users/{userId}/requests")
    public List<ParticipationRequestDto> getParticipationRequests(@PathVariable @Valid @Positive Long userId) {
        return participationRequestService.getParticipationRequests(userId);
    }

    /**
     * Получение информации о запросах на участие в событии текущего пользователя
     * @param userId id текущего пользователя
     * @param eventId id события
     * @return Запросы на участие
     */
    @GetMapping("/users/{userId}/events/{eventId}/requests")
    public List<ParticipationRequestDto> getParticipationRequestsForUserEvent(@PathVariable @Valid @Positive Long userId,
                                                                              @PathVariable @Valid @Positive Long eventId) {
        return participationRequestService.getParticipationRequestsForUserEvent(userId, eventId);
    }

    /**
     * Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя
     * @param userId id текущего пользователя
     * @param eventId id события текущего пользователя
     * @param eventRequestStatusUpdateRequest Новый статус для заявок на участие в событии текущего пользователя
     * @return Результат подтверждения/отклонения заявок на участие в событии
     */
    @PatchMapping("/users/{userId}/events/{eventId}/requests")
    public EventRequestStatusUpdateResultDto changeParticipationRequestsStatus(@PathVariable @Valid @Positive Long userId,
                                                                               @PathVariable @Valid @Positive Long eventId,
                                                                               @RequestBody EventRequestStatusUpdateRequestDto eventRequestStatusUpdateRequest) {
        return participationRequestService.changeParticipationRequestsStatus(userId, eventId, eventRequestStatusUpdateRequest);
    }

}
