package kg.attractor.jobsearch.controller.api;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.EmployerService;
import kg.attractor.jobsearch.service.FileService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/employers")
@RequiredArgsConstructor
public class EmployerController {
    private final UserService userService;
    private final VacancyService vacancyService;
    private final FileService fileService;
    private final EmployerService employerService;

    @GetMapping("/vacancies/active")
    public List<VacancyDto> getAllActiveVacancies() {
        return vacancyService.getVacancies().stream()
                .filter(VacancyDto::getIsActive)
                .collect(Collectors.toList());
    }

    @GetMapping("/vacancies/category/{category}")
    public List<VacancyDto> getVacanciesByCategory(@PathVariable String category) {
        return vacancyService.getVacanciesByCategory(category);
    }

    @GetMapping("/{employerId}")
    public UserDto getEmployerById(@PathVariable Integer employerId) {
        return userService.getUserById(employerId);
    }

    @GetMapping("/download-avatar/{filename}")
    public ResponseEntity<?> downloadImage(@PathVariable String filename) {
        return fileService.getOutputFile(filename, "/avatars", MediaType.IMAGE_JPEG);
    }

    @PostMapping("/resumes")
    public void createResume(@RequestBody UserDto userDto) {
        userService.addUser(userDto);
    }

//    @PostMapping("/vacancies/{vacancyId}/apply")
//    public void applyToVacancy(@PathVariable Integer vacancyId, @RequestBody UserDto userDto) {
//        vacancyService.applyToVacancy(vacancyId, userDto);
//    }

    // Загрузка аватара
    @PostMapping("/upload-avatar")
    public void uploadAvatar(@RequestParam("userId") Integer userId, @RequestParam("file") MultipartFile file) {
        userService.uploadAvatar(file, userId);
    }

    @PostMapping("/vacancy")
    public void createVacancy(@RequestBody VacancyDto vacancyDto) {
        employerService.createVacancy(vacancyDto);
    }

    @PutMapping("/{id}")
    public void updateVacancy(@PathVariable Integer id, @RequestBody VacancyDto vacancyDto) {
        employerService.updateVacancy(id, vacancyDto);
    }

    @PutMapping("/resumes/{id}")
    public void updateResume(@PathVariable Integer id, @RequestBody UserDto userDto) {
        userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    public void deleteVacancy(@PathVariable Integer id) {
        employerService.deleteVacancy(id);
    }

    @DeleteMapping("/resumes/{id}")
    public void deleteResume(@PathVariable Integer id) {
        userService.deleteUser(id);
    }
}


