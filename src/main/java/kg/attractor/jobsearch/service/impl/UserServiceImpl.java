package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final List<UserDto> users = new ArrayList<>();
    private final UserDao userDao;
    private static final String UPLOAD_DIR = "uploads/avatars/";

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
        if (userDto.getAvatar() == null || userDto.getAvatar().isEmpty()) {
            userDto.setAvatar("ava.png");
        }
        User user = new User();
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAvatar(userDto.getAvatar());
        user.setAccountType(userDto.getAccountType());

        userDao.create(user);
        if (user.getAccountType().equals("applicant")) {
            log.info("added applicant with email {}", user.getEmail());
        } else {
            log.info("added employer with email {}", user.getEmail());
        }
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
    public void uploadAvatar(MultipartFile file, Integer userId) {
        String avatarFileName = saveUploadedFile(file, "");
        UserDto user = getUserById(userId);
        if (user != null) {
            user.setAvatar(UPLOAD_DIR + avatarFileName);
            updateUser(userId, user);
        } else {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
    }

    @SneakyThrows
    @Override
    public String saveUploadedFile(MultipartFile file, String subDir) {
        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "_" + file.getOriginalFilename();

        Path pathDir = Paths.get(UPLOAD_DIR + subDir);
        Files.createDirectories(pathDir);

        Path filePath = pathDir.resolve(resultFileName);
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

    @Override
    public List<UserDto> getUserList() {
        return getUsers();
    }

    @Override
    public void createUser(UserDto user) {
        addUser(user);
    }
}