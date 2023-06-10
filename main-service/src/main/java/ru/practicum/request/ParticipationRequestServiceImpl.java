package ru.practicum.request;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.Event;
import ru.practicum.event.EventRepository;
import ru.practicum.event.EventState;
import ru.practicum.exceptions.*;
import ru.practicum.request.dto.EventRequestStatusUpdateRequestDto;
import ru.practicum.request.dto.EventRequestStatusUpdateResultDto;
import ru.practicum.request.dto.ParticipationRequestDto;
import ru.practicum.request.dto.ParticipationRequestMapper;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.request.dto.ParticipationRequestMapper.toParticipationRequestDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository participationRequestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public ParticipationRequestDto createParticipationRequest(Long userId, Long eventId) {
        //проверить, что существует пользователь с указанным userId
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        //проверить, что существует событие с указанным eventId
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));

        //нельзя добавить повторный запрос (Ожидается код ошибки 409)
        ParticipationRequest existParticipationRequest = participationRequestRepository.findByRequesterIdAndEventId(userId, eventId);
        if (existParticipationRequest != null) {
            log.info("Error: нельзя добавить повторный запрос");
            throw new ForbiddenException("Could not add the same request.");
        }
        //инициатор события не может добавить запрос на участие в своём событии (Ожидается код ошибки 409)
        if (event.getInitiator().getId().equals(userId)) {
            log.info("Error: инициатор события не может добавить запрос на участие в своём событии");
            throw new ForbiddenException("Initiator could not add request to own event.");
        }
        //нельзя участвовать в неопубликованном событии (Ожидается код ошибки 409)
        if (event.getState() != EventState.PUBLISHED) {
            log.info("Error: нельзя участвовать в неопубликованном событии");
            throw new ForbiddenException("Could not participate in non-published event.");
        }
        //если у события достигнут лимит запросов на участие - необходимо вернуть ошибку (Ожидается код ошибки 409)
        if (event.getParticipantLimit() != 0 && participationRequestRepository.countByEventIdAndStatus(eventId, ParticipationRequestStatus.CONFIRMED) >= event.getParticipantLimit()) {
            log.info("Error: достигнут лимит запросов на участие");
            throw new ForbiddenException("Reach participant limit.");
        }

        //если для события отключена пре-модерация запросов на участие, то запрос должен автоматически перейти в состояние подтвержденного
        ParticipationRequestStatus status = ParticipationRequestStatus.PENDING;
        if (!event.isRequestModeration() || event.getParticipantLimit() == 0)
            status = ParticipationRequestStatus.CONFIRMED;

        ParticipationRequest newParticipationRequest = ParticipationRequest.builder()
                .event(event)
                .requester(user)
                .created(LocalDateTime.now())
                .status(status)
                .build();

        return toParticipationRequestDto(participationRequestRepository.save(newParticipationRequest));
    }

    @Override
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        ParticipationRequest requestToUpdate = participationRequestRepository.getReferenceById(requestId);
        requestToUpdate.setStatus(ParticipationRequestStatus.CANCELED);
        return toParticipationRequestDto(participationRequestRepository.save(requestToUpdate));
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequests(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<ParticipationRequest> requests = participationRequestRepository.findByRequesterId(userId);

        return requests != null ?
                requests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequestsForUserEvent(Long userId, Long eventId) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        List<Event> userEvents = eventRepository.findByIdAndInitiatorId(eventId, userId);
        List<ParticipationRequest> requests = participationRequestRepository.findByEventIn(userEvents);

        return requests != null ?
                requests.stream()
                .map(ParticipationRequestMapper::toParticipationRequestDto)
                .collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResultDto changeParticipationRequestsStatus(Long userId, Long eventId,
                                                                               EventRequestStatusUpdateRequestDto eventRequestStatusUpdateRequest) {
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        List<ParticipationRequest> requests = participationRequestRepository.findAllById(eventRequestStatusUpdateRequest.getRequestIds());

        EventRequestStatusUpdateResultDto eventRequestStatusUpdateResultDto = EventRequestStatusUpdateResultDto.builder()
                .confirmedRequests(new ArrayList<>())
                .rejectedRequests(new ArrayList<>())
                .build();

        if (!requests.isEmpty()) {
            if (ParticipationRequestStatus.valueOf(eventRequestStatusUpdateRequest.getStatus()) == ParticipationRequestStatus.CONFIRMED) {
                int limitParticipants = event.getParticipantLimit();
                //если для события лимит заявок равен 0 или отключена пре-модерация заявок, то подтверждение заявок не требуется
                if (limitParticipants == 0 || !event.isRequestModeration())
                    throw new ForbiddenException("Do not need accept requests, cause participant limit equals 0 or " +
                            "pre-moderation off");
                Integer countParticipants = participationRequestRepository.countByEventIdAndStatus(event.getId(), ParticipationRequestStatus.CONFIRMED);
                //нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие (Ожидается код ошибки 409)
                if (countParticipants == limitParticipants)
                    throw new ForbiddenException("The participant limit has been reached");

                for (ParticipationRequest request : requests) {
                    //статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409)
                    if (request.getStatus() != ParticipationRequestStatus.PENDING)
                        throw new ForbiddenException("Status of request doesn't PENDING");
                    //если при подтверждении данной заявки, лимит заявок для события исчерпан, то все неподтверждённые заявки необходимо отклонить
                    if (countParticipants < limitParticipants) {
                        request.setStatus(ParticipationRequestStatus.CONFIRMED);
                        eventRequestStatusUpdateResultDto.getConfirmedRequests().add(toParticipationRequestDto(request));
                        countParticipants++;
                    } else {
                        request.setStatus(ParticipationRequestStatus.REJECTED);
                        eventRequestStatusUpdateResultDto.getRejectedRequests().add(toParticipationRequestDto(request));
                    }
                }
                participationRequestRepository.saveAll(requests);
                //если лимит заявок исчерпан, ищем оставшиеся ожидающие подтверждения заявки и отклоняем их
                if (countParticipants == limitParticipants) {
                    participationRequestRepository.updateRequestStatusByEventIdAndStatus(event,
                            ParticipationRequestStatus.PENDING, ParticipationRequestStatus.REJECTED);
                }
            } else if (ParticipationRequestStatus.valueOf(eventRequestStatusUpdateRequest.getStatus()) == ParticipationRequestStatus.REJECTED) {
                for (ParticipationRequest request : requests) {
                    //статус можно изменить только у заявок, находящихся в состоянии ожидания (Ожидается код ошибки 409)
                    if (request.getStatus() != ParticipationRequestStatus.PENDING)
                        throw new ForbiddenException("Status of request doesn't PENDING");
                    request.setStatus(ParticipationRequestStatus.REJECTED);
                    eventRequestStatusUpdateResultDto.getRejectedRequests().add(toParticipationRequestDto(request));
                }
                participationRequestRepository.saveAll(requests);
            }

        }
        return eventRequestStatusUpdateResultDto;
    }

}
