package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.FileService;
import kg.attractor.jobsearch.service.UserService;

import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employers")
@RequiredArgsConstructor
public class EmployerController {
    private final UserService userService;
    private final VacancyService vacancyService;
    private final FileService fileService;

    @PostMapping("/resumes")
    public void createResume(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }


    @PutMapping("/resumes/{id}")
    public void updateResume(@PathVariable Integer id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
    }


    @DeleteMapping("/resumes/{id}")
    public void deleteResume(@PathVariable Integer id) {
        userService.deleteUser(id);
    }

    @GetMapping("/vacancies/active")
    public List<VacancyDto> getAllActiveVacancies() {
        return vacancyService.getAllVacancies().stream()
                .filter(VacancyDto::getIsActive)
                .collect(Collectors.toList());
    }


    @GetMapping("/vacancies/category/{category}")
    public List<VacancyDto> getVacanciesByCategory(@PathVariable String category) {
        return vacancyService.getVacanciesByCategory(category);
    }

    @PostMapping("/vacancies/{vacancyId}/apply")
    public void applyToVacancy(@PathVariable Integer vacancyId, @RequestBody UserDto userDto) {
        vacancyService.applyToVacancy(vacancyId, userDto);
    }


    @GetMapping("/employers/{employerId}")
    public UserDto getEmployerById(@PathVariable Integer employerId) {
        return userService.getUserById(employerId);
    }
    // Загрузка аватара
    @PostMapping("/upload-avatar")
    public void uploadAvatar(@RequestParam("file") MultipartFile file) {
        userService.uploadAvatar(file);
    }

    // Скачивание изображения
    @GetMapping("/download-avatar/{filename}")
    public ResponseEntity<?> downloadImage(@PathVariable String filename) {
        return fileService.getOutputFile(filename, "/avatars", MediaType.IMAGE_JPEG);
    }

}