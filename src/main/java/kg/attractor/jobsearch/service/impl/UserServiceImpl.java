package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String UPLOAD_DIR = "uploads/";

    @Override
    public List<UserDto> getUsers() {
        // Реализуйте логику получения всех пользователей
        return null;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        // Реализуйте логику получения пользователя по email
        return null;
    }

    @Override
    public UserDto getUserById(Integer id) {
        // Реализуйте логику получения пользователя по ID
        return null;
    }

    @Override
    public void addUser(UserDto userDto) {
        // Реализуйте логику добавления нового пользователя
    }

    @Override
    public void updateUser(Integer id, UserDto userDto) {
        // Реализуйте логику обновления пользователя
    }

    @Override
    public boolean deleteUser(Integer id) {
        // Реализуйте логику удаления пользователя
        return false;
    }

    @Override
    public void uploadAvatar(MultipartFile file) {
        // Реализуйте логику загрузки аватара
        saveUploadedFile(file, "/avatars");
    }

    @Override
    @SneakyThrows
    public String saveUploadedFile(MultipartFile file, String subDir) {
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "_" + file.getOriginalFilename();

        Path pathDir = Paths.get(UPLOAD_DIR + subDir);
        Files.createDirectories(pathDir);

        Path filePath = Paths.get(pathDir + "/" + resultFileName);
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
        try (OutputStream os = Files.newOutputStream(filePath)) {
            os.write(file.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return resultFileName;
    }
}