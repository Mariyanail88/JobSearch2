package kg.attractor.jobsearch.service.impl;


import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.UserWithAvatarFileDto;
import kg.attractor.jobsearch.errors.UserNotFoundException;
import kg.attractor.jobsearch.mappers.CustomUserMapper;
import kg.attractor.jobsearch.mappers.UserMapper;
import kg.attractor.jobsearch.model.RespondedApplicant;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.Role;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.RespondedApplicantsRepository;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.repository.RoleRepository;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.ConsoleColors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final VacancyRepository vacancyRepository;
    private final ResumeRepository resumeRepository;
    private final RoleRepository roleRepository;

    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final PasswordEncoder encoder;

    @Value("${app.avatar_dir}")
    private String AVATAR_DIR;
    private RespondedApplicantsRepository respondedApplicantRepository;

    @Override
    public List<UserDto> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public List<UserDto> getUsersByName(String name) {
        return userRepository.findByName(name)
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    @Override
    public UserDto getUserByPhone(String phoneNumber) throws UserNotFoundException {
        User user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new UserNotFoundException("Can't find user with phone: " + phoneNumber)
        );
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserByEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("Can't find user with email: " + email)
        );
        return userMapper.toUserDto(user);
    }

    @Override
    public UserDto getUserById(Integer id) {//throws UserNotFoundException {
        User user = userRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Can't find user with ID: {}", id);
                    return new NoSuchElementException("Can't find user with ID: " + id);
                }
        );
        log.info("User found with ID: {}", id);
        return userMapper.toUserDto(user);
    }

    @Override
    public void addUser(UserDto userDto) {
        if (userDto.getAvatar() == null || userDto.getAvatar().isEmpty()) {
            userDto.setAvatar("default_user_avatar.png");
            log.info(ConsoleColors.YELLOW +
                            "user with email {} didn't choose avatar, so default avatar is set" +
                            ConsoleColors.RESET,
                    userDto.getEmail()
            );
        }
        User user = CustomUserMapper.toUser(userDto);

        if (user.getPassword().length() < 20) {
            user.setPassword(encoder.encode(user.getPassword()));
        }

        user.setRoles(initializeCollectionIfNull(user.getRoles()));

        Role userRole = roleRepository.findByRoleName(user.getAccountType().toUpperCase()).orElseThrow(
                () -> new NoSuchElementException("Role not found")
        );

        user.getRoles().add(userRole);
        userRepository.save(user);

        log.info(
                ConsoleColors.ANSI_BLUE +
                        "User saved to DB with email {} and role {}" +
                        ConsoleColors.RESET,
                userDto.getEmail(),
                userRole.getRoleName()
        );



        if (user.getAccountType().equals("applicant")) {
            log.info("added applicant with email {}", user.getEmail());
        } else {
            log.info("added employer with email {}", user.getEmail());
        }
    }

    @Override
    public void addUserWithAvatar(UserWithAvatarFileDto userWithAvatarFileDto) throws UserNotFoundException, IOException {
        if (userWithAvatarFileDto.getAvatar().isEmpty()) {
            addUser(CustomUserMapper.toUserDto(userWithAvatarFileDto));
        } else {
            addUser(CustomUserMapper.toUserDto(userWithAvatarFileDto));
            Optional<User> user = userRepository.findByEmail(userWithAvatarFileDto.getEmail());
            if (user.isPresent()) {
                saveAvatar(
                        user.get().getId(),
                        userWithAvatarFileDto.getAvatar()
                );
                UserDto userWithUpdatedAvatar = getUserById(user.get().getId());
                log.info(ConsoleColors.YELLOW_BACKGROUND + "saved avatar {}" + ConsoleColors.RESET, userWithUpdatedAvatar.getAvatar());
            } else {
                log.error("Can't find user with email: {}", userWithAvatarFileDto.getEmail());
                throw new UserNotFoundException("Can't find user with email: " + userWithAvatarFileDto.getEmail());
            }
        }
    }

    @Override
    public List<UserDto> getUsersRespondedToVacancy(Integer vacancyId) {
        List<UserDto> users = userRepository.findUsersRespondedToVacancy(vacancyId)
                .stream()
                .map(CustomUserMapper::toUserDto)
                .toList();
        if (users.isEmpty()) {
            log.error("Can't find users with vacancy id {}", vacancyId);
        } else {
            log.info("found users with vacancy id {}", vacancyId);
        }
        return users;
    }

    @Override
    public void applyForVacancy(Integer vacancyId, ResumeDto resumeDto) {
        Resume resume = resumeRepository.findById(resumeDto.getId()).orElseThrow(
                () -> new UsernameNotFoundException(
                        "Can't find resume with applicant id: " + resumeDto.getApplicantId()
                )
        );
        Vacancy vacancy = vacancyRepository.findById(vacancyId).orElseThrow(
                () -> new NoSuchElementException("Can't find vacancy with ID: " + vacancyId)
        );
//        vacancyDao.applyForVacancy(resume.getId(), vacancy.getId());
        respondedApplicantRepository.save(
                new RespondedApplicant(
                        null,
                        resume.getId(),
                        vacancyId,
                        true
                )
        );
        log.info("apply for vacancy {} successfully", vacancy.getName());
    }

    @Override
    public void saveAvatar(Integer userId, MultipartFile avatar) throws IOException, UserNotFoundException {
        if (!avatar.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + avatar.getOriginalFilename();
            if (fileName.length() > 45) {
                fileName = fileName.substring(fileName.length() - 45);
            }
            Path filePath = Paths.get(AVATAR_DIR, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, avatar.getBytes());

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new UserNotFoundException("can't find user with id: " + userId));

            userRepository.updateAvatar(user.getId(), fileName);

            log.warn(ConsoleColors.YELLOW_BACKGROUND + "saved avatar to path {}" + ConsoleColors.RESET, filePath.getFileName().getFileName());
        } else {
            throw new IOException("File is empty");
        }
    }

    @Override
    public ResponseEntity<Resource> getAvatar(Integer userId) throws UserNotFoundException {
//        Optional<User> user = userDao.getUserById(userId);
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            try {
                byte[] image = Files.readAllBytes(Paths.get(AVATAR_DIR + user.get().getAvatar()));
                Resource resource = new ByteArrayResource(image);
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + user.get().getAvatar() + "\"")
                        .contentLength(resource.contentLength())
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } else {
            log.error("Can't find user with id {}", userId);
            throw new UserNotFoundException(String.format("user with id %d not found", userId));
        }
    }

    @Override
    public void updateUser(UserWithAvatarFileDto userWithAvatarFileDto) throws IOException, UserNotFoundException {
        UserDto existingUser = getUserByEmail(userWithAvatarFileDto.getEmail());
        userWithAvatarFileDto.setPassword(existingUser.getPassword());
        if (userWithAvatarFileDto.getAvatar() == null || userWithAvatarFileDto.getAvatar().isEmpty()) {
            log.info(ConsoleColors.YELLOW +
                            "user with email {} didn't choose new avatar, so previous avatar is used" +
                            ConsoleColors.RESET,
                    userWithAvatarFileDto.getEmail()
            );
            UserDto updatedUser = CustomUserMapper.toUserDto(userWithAvatarFileDto);
            updatedUser.setAvatar(existingUser.getAvatar());
            addUser(updatedUser);
        } else {
            addUserWithAvatar(userWithAvatarFileDto);
        }
    }

    @Override
    public boolean deleteUser(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.delete(user.get());
            log.info("user deleted: {}", user.get().getName());
            return true;
        }
        log.info(String.format("user with id %d not found", id));
        return false;
    }
    // Отправка токена для сброса пароля
    @Override
    public void sendPasswordResetToken(String email) throws UserNotFoundException, MessagingException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        String token = UUID.randomUUID().toString();
        user.setResetToken(token);
        userRepository.save(user);

        String resetLink = "http://localhost:8080/auth/reset_password?token=" + token;
        sendEmail(user.getEmail(), resetLink);
    }


    // Вспомогательный метод для отправки email
    private void sendEmail(String to, String resetLink) throws MessagingException {
     //   MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//        helper.setTo(to);
//        helper.setSubject("Password Reset Request");
//        helper.setText("<p>Click the link below to reset your password:</p>"
//                + "<p><a href=\"" + resetLink + "\">Reset Password</a></p>", true);
//
//        mailSender.send(message);
    }

    // Сброс пароля по токену
    @Override
    public void resetPassword(String token, String password) throws UserNotFoundException {
        User user = userRepository.findByResetToken(token)
                .orElseThrow(() -> new UserNotFoundException("Invalid token"));

        user.setPassword(encoder.encode(password));
        user.setResetToken(null);
        userRepository.save(user);
    }

    private <T> Collection<T> initializeCollectionIfNull(Collection<T> collection) {
        return collection == null ? new HashSet<>() : collection;
    }

}
