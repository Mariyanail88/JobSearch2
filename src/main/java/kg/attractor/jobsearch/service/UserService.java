package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers();
    UserDto getUserByEmail(String email);
    UserDto getUserById(Integer id);
    void addUser(UserDto userDto);
    void updateUser(Integer id, UserDto userDto);
    boolean deleteUser(Integer id);
    void uploadAvatar(MultipartFile file);
    String saveUploadedFile(MultipartFile file, String subDir); // Новый метод
}