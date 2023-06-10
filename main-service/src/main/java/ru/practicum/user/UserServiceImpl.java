package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.user.dto.NewUserRequestDto;
import ru.practicum.user.dto.UserDto;

import java.util.List;

import static ru.practicum.user.dto.UserMapper.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        return toUserDto(userRepository.findByIdIn(ids, PageRequest.of(from / size, size)));
    }

    public List<UserDto> getUsers(int from, int size) {
        return toUserDto(userRepository.findAll(PageRequest.of(from / size, size)));
    }

    public UserDto createUser(NewUserRequestDto newUserRequestDto) {
        return toUserDto(userRepository.save(toUser(newUserRequestDto)));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
