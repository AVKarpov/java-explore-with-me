package ru.practicum.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * Получение списка категорий
     * @param from количество категорий, которые нужно пропустить для формирования текущего набора (по умолчанию 0)
     * @param size количество категорий в наборе (по умолчанию 10)
     * @return Список категорий или пустой список
     */
    @GetMapping("/categories")
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") int from,
                                           @RequestParam(defaultValue = "10") int size) {
        return categoryService.getCategories(from, size);
    }

    /**
     * Получение информации о категории по её идентификатору
     * @param catId id категории
     * @return Описание категории, либо если не найдена возвращает статус код 404
     */
    @GetMapping("/categories/{catId}")
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        return categoryService.getCategoryById(catId);
    }

    /**
     * Добавление новой категории
     * @param newCategoryDto Название добавляемой категории (должно быть уникальным)
     * @return Описание категории, либо ошибка 400/409
     */
    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(@RequestBody @Validated NewCategoryDto newCategoryDto) {
        return categoryService.createCategory(newCategoryDto);
    }

    /**
     * Изменение категории
     * @param newCategoryDto Название изменяемой категории (должно быть уникальным)
     * @return Описание категории, либо ошибка 400/409
     */
    @PatchMapping("/admin/categories/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@PathVariable Long catId,
                                      @RequestBody @Validated NewCategoryDto newCategoryDto) {
        return categoryService.updateCategory(catId, newCategoryDto);
    }

    /**
     * Удаление категории
     * @param catId id категории
     */
    @DeleteMapping(value = "/admin/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        categoryService.deleteCategory(catId);
    }

}
