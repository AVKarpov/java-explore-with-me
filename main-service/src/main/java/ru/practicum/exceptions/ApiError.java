package ru.practicum.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiError {

    /**
     * Код статуса HTTP-ответа ("FORBIDDEN")
     */
    private String status;

    /**
     * Общее описание причины ошибки ("Incorrectly made request.")
     */
    private String reason;

    /**
     * Сообщение об ошибке ("Field: name. Error: must not be blank. Value: null")
     */
    private String message;

    /**
     * Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
     */
    private String timestamp;

}