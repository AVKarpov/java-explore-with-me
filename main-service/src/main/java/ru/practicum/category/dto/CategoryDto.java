package ru.practicum.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    /**
     * Идентификатор категории
     */
    private Long id;

    /**
     * Название категории (от 1 до 50 символов)
     */
    private String name;

}
