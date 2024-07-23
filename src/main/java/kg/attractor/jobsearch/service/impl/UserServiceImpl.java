package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public List<UserDto> getUsers() {

        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {

        return null;
    }

    @Override
    public UserDto getUserById(Integer id) {

        return null;
    }

    @Override
    public void addUser(UserDto userDto) {
    }

    @Override
    public boolean deleteUser(Long id) {
        return false;
    }

    @Override
    public List<UserDto> getUsersRespondedToVacancy(Integer vacancyId) {
        return null;
    }

    @Override
    public void updateUser(Integer id, UserDto userDto) {

    }

    @Override
    public boolean deleteUser(Integer id) {
        return false;
    }

    @Override
    public void uploadAvatar(MultipartFile file) {

    }
}