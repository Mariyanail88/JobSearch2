package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;


import java.util.List;

public class UserServiceImpl implements UserService {

    // Получает список всех пользователей
    @Override
    public List<UserDto> getUsers() {
        return null;
    }

    // Получает пользователя по его email
    @Override
    public UserDto getUserByEmail(String email) {
        return null;
    }

    // Получает пользователя по его ID
    @Override
    public UserDto getUserById(Integer id) {
        return null;
    }

    // Добавляет нового пользователя
    @Override
    public void addUser(UserDto userDto) {

    }

    // Удаляет пользователя по его ID
    @Override
    public boolean deleteUser(Long id) {
        return false;
    }

    // Получает список пользователей, откликнувшихся на вакансию
    @Override
    public List<UserDto> getUsersRespondedToVacancy(Integer vacancyId) {
        return null;
    }
}