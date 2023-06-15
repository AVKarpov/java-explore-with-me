package ru.practicum.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comment.dto.CommentResponseDto;
import ru.practicum.comment.dto.NewCommentDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * Добавление комментария
     * @param userId id пользователя
     * @param eventId id события
     * @param newCommentDto Комментарий
     * @return Краткая информация по комментарию
     */
    @PostMapping("/user/{userId}/events/{eventId}/comment/")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto createComment(@PathVariable @Valid @Positive Long userId,
                                            @PathVariable @Valid @Positive Long eventId,
                                            @RequestBody @Validated NewCommentDto newCommentDto) {
        return commentService.createComment(userId, eventId, newCommentDto);
    }

    /**
     * Получение всех комментариев, относящихся к событию
     * @param eventId id события
     * @param from количество комментариев, которые нужно пропустить для формирования текущего набора
     * @param size количество комментариев в наборе
     * @return Список краткой информации по комментариям
     */
    @GetMapping("/events/{eventId}/comments")
    public List<CommentResponseDto> getEventComments(@PathVariable @Valid @Positive Long eventId,
                                                     @RequestParam(defaultValue = "0") int from,
                                                     @RequestParam(defaultValue = "10") int size) {
        return commentService.getEventComments(eventId, from, size);
    }

    /**
     * Получение комментария по его идентификатору
     * @param commentId id комментария
     * @return Краткая информация по комментарию
     */
    @GetMapping("/comment/{commentId}")
    public CommentResponseDto getCommentById(@PathVariable @Valid @Positive Long commentId) {
        return commentService.getCommentById(commentId);
    }

    /**
     * Обновление комментария (нельзя обновить комментарий, прошедший модерацию)
     * @param userId id пользователя
     * @param commentId id комментария
     * @param newCommentDto Новый комментарий
     * @return Краткая информация по комментарию
     */
    @PatchMapping("/user/{userId}/comment/{commentId}")
    public CommentResponseDto updateComment(@PathVariable @Valid @Positive Long userId,
                                            @PathVariable @Valid @Positive Long commentId,
                                            @RequestBody @Validated NewCommentDto newCommentDto) {
        return commentService.updateComment(userId, commentId, newCommentDto);
    }

    /**
     * Удаление комментария
     * @param userId id пользователя
     * @param commentId id комментария
     */
    @DeleteMapping("/user/{userId}/comment/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable @Valid @Positive Long userId,
                              @PathVariable @Valid @Positive Long commentId) {
        commentService.deleteComment(userId, commentId);
    }

    /**
     * Изменение статуса у комментария администратором (опубликовать/отколнить)
     * @param commentId id комментария
     * @param isConfirm опубликовать или отклонить
     * @return Обновленная краткая информация по комментарию с его статусом
     */
    @PatchMapping("/admin/comment/{commentId}")
    public CommentResponseDto updateCommentStatusByAdmin(@PathVariable @Valid @Positive Long commentId,
                                                         @RequestParam boolean isConfirm) {
        return commentService.updateCommentStatusByAdmin(commentId, isConfirm);
    }

}
