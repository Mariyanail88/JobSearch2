package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final List<UserDto> users = new ArrayList<>();

    @Override
    public List<UserDto> getUsers() {
        return new ArrayList<>(users);
    }

    @Override
    public UserDto getUserByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public UserDto getUserById(Integer id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void addUser(UserDto userDto) {
        users.add(userDto);
    }

    @Override
    public void updateUser(Integer id, UserDto userDto) {
        UserDto existingUser = getUserById(id);
        if (existingUser != null) {
            existingUser.setName(userDto.getName());
            existingUser.setAge(userDto.getAge());
            existingUser.setEmail(userDto.getEmail());
            existingUser.setPassword(userDto.getPassword());
            existingUser.setPhoneNumber(userDto.getPhoneNumber());
            existingUser.setAvatar(userDto.getAvatar());
            existingUser.setAccountType(userDto.getAccountType());
        }
    }

    @Override
    public boolean deleteUser(Integer id) {
        return users.removeIf(user -> user.getId().equals(id));
    }

    @Override
    public void uploadAvatar(MultipartFile file) {
        // Реализация загрузки аватара
    }

    @Override
    public String saveUploadedFile(MultipartFile file, String subDir) {
        // Реализация сохранения загруженного файла
        return null;
    }

    @Override
    public List<UserDto> getUserList() {
        return getUsers();
    }
}