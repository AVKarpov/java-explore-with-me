package ru.practicum.compilation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CompilationController {

    private final CompilationService compilationService;

    /**
     * Получение подборок событий
     * @param pinned искать только закрепленные/не закрепленные подборки
     * @param from количество элементов, которые нужно пропустить для формирования текущего набора
     * @param size количество элементов в наборе
     * @return Список подборок событий
     */
    @GetMapping("/compilations")
    public List<CompilationDto> getCompilations(@RequestParam(required = false) Boolean pinned,
                                                @RequestParam(defaultValue = "0") int from,
                                                @RequestParam(defaultValue = "10") int size) {
        return compilationService.getCompilations(pinned, from, size);
    }

    /**
     * Получение подборки событий по его id
     * @param compId id подборки
     * @return Подборка событий
     */
    @GetMapping("/compilations/{compId}")
    public CompilationDto getCompilationById(@PathVariable @Valid @Positive Long compId) {
        return compilationService.getCompilationById(compId);
    }

    /**
     * Добавление новой подборки (подборка может не содержать событий)
     * @param newCompilationDto данные новой подборки
     * @return Подборка событий
     */
    @PostMapping("/admin/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(@RequestBody @Validated NewCompilationDto newCompilationDto) {
        return compilationService.createCompilation(newCompilationDto);
    }

    /**
     * Обновление информации о подборке
     * @param compId id подборки
     * @param updateCompilationRequestDto Данные для обновления подборки
     * @return Подборка событий
     */
    @PatchMapping("/admin/compilations/{compId}")
    public CompilationDto updateCompilation(@PathVariable @Valid @Positive Long compId,
                                         @RequestBody @Validated UpdateCompilationRequestDto updateCompilationRequestDto) {
        return compilationService.updateCompilation(compId, updateCompilationRequestDto);
    }

    /**
     * Удаление подборки
     * @param compId id подборки
     */
    @DeleteMapping(value = "/admin/compilations/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(@PathVariable Long compId) {
        compilationService.deleteCompilation(compId);
    }

}
