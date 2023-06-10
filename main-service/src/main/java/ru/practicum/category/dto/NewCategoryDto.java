package ru.practicum.category.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@RequiredArgsConstructor
public class NewCategoryDto {

    /**
     * Название категории (от 1 до 50 символов)
     */
    @Size(min = 1, max = 50)
    @NotNull @NotBlank
    private String name;

}
