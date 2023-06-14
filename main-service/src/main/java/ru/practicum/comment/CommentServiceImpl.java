package ru.practicum.comment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.comment.dto.CommentMapper;
import ru.practicum.comment.dto.CommentResponseDto;
import ru.practicum.comment.dto.NewCommentDto;
import ru.practicum.event.Event;
import ru.practicum.event.EventRepository;
import ru.practicum.event.EventState;
import ru.practicum.exceptions.CommentNotFoundException;
import ru.practicum.exceptions.EventNotFoundException;
import ru.practicum.exceptions.ForbiddenException;
import ru.practicum.exceptions.UserNotFoundException;
import ru.practicum.user.User;
import ru.practicum.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.comment.dto.CommentMapper.toComment;
import static ru.practicum.comment.dto.CommentMapper.toCommentResponseDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public CommentResponseDto createComment(Long userId, Long eventId, NewCommentDto newCommentDto) {
        log.info("Create comment from user = " + userId + ", for event = " + eventId + ": " + newCommentDto);
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Event event = eventRepository.findByIdAndState(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        Comment comment = toComment(newCommentDto);
        comment.setEvent(event);
        comment.setAuthor(user);
        comment.setState(CommentState.PENDING);
        comment.setCreatedOn(LocalDateTime.now());
        return toCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponseDto> getEventComments(Long eventId, int from, int size) {
        log.info("Get comments for event = " + eventId);
        Event event = eventRepository.findByIdAndState(eventId, EventState.PUBLISHED)
                .orElseThrow(() -> new EventNotFoundException(eventId));
        List<Comment> comments = commentRepository.findByEvent(event, PageRequest.of(from / size, size));

        return comments.stream().map(CommentMapper::toCommentResponseDto).collect(Collectors.toList());
    }

    @Override
    public CommentResponseDto getCommentById(Long commentId) {
        log.info("Get comment by id = " + commentId);
        return toCommentResponseDto(commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId)));
    }

    @Override
    public CommentResponseDto updateComment(Long userId, Long commentId, NewCommentDto newCommentDto) {
        log.info("Update comment with id = " + commentId + " from user = " + userId + ": " + newCommentDto);
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        //нельзя обновить чужой комментарий
        if (!comment.getAuthor().getId().equals(userId))
            throw new ForbiddenException("Can not update comment from other user.");
        //нельзя отредактировать комментарий, если он уже опубликован
        if (comment.getState() == CommentState.CONFIRMED)
            throw new ForbiddenException("Can not update confirmed comment.");
        comment.setText(newCommentDto.getText());
        comment.setUpdatedOn(LocalDateTime.now());
        comment.setState(CommentState.PENDING);
        return toCommentResponseDto(commentRepository.save(comment));
    }

    @Override
    public void deleteComment(Long userId, Long commentId) {
        log.info("Delete comment with id = " + commentId + " from user = " + userId);
        userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        //нельзя удалить чужой комментарий
        if (!comment.getAuthor().getId().equals(userId))
            throw new ForbiddenException("Can not delete comment from other user.");
        //нельзя удалить комментарий, прошедший модерацию админом
        if (comment.getState() == CommentState.CONFIRMED)
            throw new ForbiddenException("Can not delete confirmed comment.");
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentResponseDto updateCommentStatusByAdmin(Long commentId, boolean isConfirm) {
        log.info("Confirm/reject comment with id = " + commentId + ". New state: " + isConfirm);
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        if (isConfirm)
            comment.setState(CommentState.CONFIRMED);
        else
            comment.setState(CommentState.REJECTED);
        comment.setUpdatedOn(LocalDateTime.now());
        return toCommentResponseDto(commentRepository.save(comment));
    }

}
