package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;


    /**
     * Добавление нового события
     * @param userId id текущего пользователя
     * @param newEventDto Данные добавляемого события
     * @return EventFullDto Полное описание события, либо ошибки 400/409
     */
    @PostMapping("/users/{userId}/events")
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto createEvent(@PathVariable @Valid @Positive Long userId,
                                    @RequestBody @Validated NewEventDto newEventDto) {
        return eventService.createEvent(userId, newEventDto);
    }

    /**
     ** Получение событий, добавленных текущим пользователем
     * @param userId id текущего пользователя
     * @param from Количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size Количество элементов в наборе
     * @return List<EventShortDto> Список кратких описаний событий, либо ошибка 400
     */
    @GetMapping("/users/{userId}/events")
    public List<EventShortDto> getEvents(@PathVariable @Valid @Positive Long userId,
                                   @RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                   @RequestParam(defaultValue = "10") @Positive int size) {
        return eventService.getEvents(userId, from, size);
    }

    /**
     ** Получение полной информации о событии, добавленном текущим пользователем
     * @param userId id текущего пользователя
     * @param eventId id события
     * @return EventFullDto Полное описание события, либо ошибка 400/404
     */
    @GetMapping("/users/{userId}/events/{eventId}")
    public EventFullDto getEventById(@PathVariable @Valid @Positive Long userId,
                                     @PathVariable @Valid @Positive Long eventId) {
        return eventService.getEventById(userId, eventId);
    }

    /**
     ** Обновление информации о событии
     * @param userId id текущего пользователя
     * @param eventId id события
     * @param updateEventUserRequestDto Новые данные события
     * @return EventFullDto Полное описание события, либо ошибка 400/404
     */
    @PatchMapping("/users/{userId}/events/{eventId}")
    public EventFullDto updateEventByUser(@PathVariable @Valid @Positive Long userId,
                                          @PathVariable @Valid @Positive Long eventId,
                                          @RequestBody @Validated UpdateEventUserRequestDto updateEventUserRequestDto) {
        return eventService.updateEventByUser(userId, eventId, updateEventUserRequestDto);
    }

    /**
     * Поиск событий
     * @param users список id пользователей, чьи события нужно найти
     * @param states список состояний в которых находятся искомые события
     * @param categories список id категорий в которых будет вестись поиск
     * @param rangeStart дата и время не раньше которых должно произойти событие
     * @param rangeEnd дата и время не позже которых должно произойти событие
     * @param from количество событий, которые нужно пропустить для формирования текущего набора
     * @param size количество событий в наборе
     * @return List<EventFullDto> Список полных описаний событий, либо ошибка 400
     */
    @GetMapping("/admin/events")
    public List<EventFullDto> getEventsByAdmin(@RequestParam(required = false) List<Long> users,
                                     @RequestParam(required = false) List<String> states,
                                     @RequestParam(required = false) List<Long> categories,
                                     @RequestParam(required = false) String rangeStart,
                                     @RequestParam(required = false) String rangeEnd,
                                     @RequestParam(defaultValue = "0") int from,
                                     @RequestParam(defaultValue = "10") int size) {
        return eventService.getEventsByAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/admin/events/{eventId}")
    public EventFullDto updateEventByAdmin(@PathVariable @Valid @Positive Long eventId,
                                           @RequestBody @Validated UpdateEventAdminRequestDto updateEventAdminRequestDto) {
        return eventService.updateEventByAdmin(eventId, updateEventAdminRequestDto);
    }

    @GetMapping("/events")
    public List<EventShortDto> getPublishedEvents(@RequestParam(required = false) String text,
                                            @RequestParam(required = false) List<Long> categories,
                                            @RequestParam(required = false) Boolean paid,
                                            @RequestParam(required = false) String rangeStart,
                                            @RequestParam(required = false) String rangeEnd,
                                            @RequestParam(defaultValue = "false") boolean onlyAvailable,
                                            @RequestParam(required = false) String sort,
                                            @RequestParam(defaultValue = "0") int from,
                                            @RequestParam(defaultValue = "10") int size,
                                            HttpServletRequest request) {

        return eventService.getPublishedEvents(text, categories, paid, rangeStart, rangeEnd, onlyAvailable,
                sort, from, size, request);
    }

    @GetMapping("/events/{id}")
    public EventFullDto getPublishedEventById(@PathVariable @Valid @Positive Long id,
                                              HttpServletRequest request) {

        return eventService.getPublishedEventById(id, request);
    }

}
