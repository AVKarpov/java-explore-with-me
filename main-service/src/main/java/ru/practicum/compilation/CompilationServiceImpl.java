package ru.practicum.compilation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.CompilationMapper;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequestDto;
import ru.practicum.event.EventRepository;
import ru.practicum.exceptions.CompilationNotFoundException;
import ru.practicum.exceptions.ValidationRequestException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.compilation.dto.CompilationMapper.toCompilation;
import static ru.practicum.compilation.dto.CompilationMapper.toCompilationDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompilationServiceImpl implements CompilationService {

    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    @Override
    public List<CompilationDto> getCompilations(Boolean pinned, int from, int size) {
        List<Compilation> compilations;
        if (pinned != null)
            compilations = compilationRepository.findByPinned(pinned, PageRequest.of(from / size, size));
        else
            compilations = compilationRepository.findAll(PageRequest.of(from / size, size)).getContent();
        return !compilations.isEmpty()
                ? compilations.stream().map(CompilationMapper::toCompilationDto).collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public CompilationDto getCompilationById(Long compId) {
        return toCompilationDto(compilationRepository.findById(compId)
                .orElseThrow(() -> new CompilationNotFoundException(compId)));
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        Compilation compilation = toCompilation(newCompilationDto);
        if (newCompilationDto.getEvents() != null) {
            compilation.setEvents(eventRepository.findByIdIn(newCompilationDto.getEvents()));
        }
        return toCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequestDto updateCompilationRequestDto) {
        Compilation compilation = compilationRepository.findById(compId).orElseThrow(() -> new CompilationNotFoundException(compId));
        if (updateCompilationRequestDto.getTitle() != null) {
            String title = updateCompilationRequestDto.getTitle();
            if (title.isEmpty() || title.length() > 50)
                throw new ValidationRequestException("Compilation title must be from 1 to 50 characters");
            compilation.setTitle(updateCompilationRequestDto.getTitle());
        }
        if (updateCompilationRequestDto.getPinned() != null)
            compilation.setPinned(updateCompilationRequestDto.getPinned());
        if (updateCompilationRequestDto.getEvents() != null)
            compilation.setEvents(eventRepository.findByIdIn(updateCompilationRequestDto.getEvents()));

        return toCompilationDto(compilationRepository.save(compilation));
    }

    @Override
    public void deleteCompilation(Long compId) {
        compilationRepository.findById(compId).orElseThrow(() -> new CompilationNotFoundException(compId));
        compilationRepository.deleteById(compId);
    }

}
