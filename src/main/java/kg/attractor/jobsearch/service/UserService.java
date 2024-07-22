package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;

import java.util.List;

public interface UserService {
    // Получает список всех пользователей
    List<UserDto> getUsers();

    // Получает пользователя по его email
    UserDto getUserByEmail(String email);

    // Получает пользователя по его ID
    UserDto getUserById(Integer id);

    // Добавляет нового пользователя
    void addUser(UserDto userDto);

    // Удаляет пользователя по его ID
    boolean deleteUser(Long id);

    // Получает список пользователей, откликнувшихся на вакансию
    List<UserDto> getUsersRespondedToVacancy(Integer vacancyId);
}
