package ru.practicum.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Пользователь (краткая информация)
 */
@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserShortDto {

    private Long id;

    private String name;

}