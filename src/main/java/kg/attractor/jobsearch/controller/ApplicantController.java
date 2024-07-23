package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.ApplicantService;
import kg.attractor.jobsearch.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicants")
@RequiredArgsConstructor
public class ApplicantController {
    private final ApplicantService applicantService;
    private final EmployerService employerService;

    @GetMapping("/resumes")
    public List<UserDto> getAllResumes() {
        return applicantService.getAllResumes();
    }

    @GetMapping("/resumes/category/{category}")
    public List<UserDto> getResumesByCategory(@PathVariable String category) {
        return applicantService.getResumesByCategory(category);
    }

    @GetMapping("/vacancy/{vacancyId}/applicants")
    public List<UserDto> getApplicantsByVacancyId(@PathVariable Integer vacancyId) {
        return applicantService.getApplicantsByVacancyId(vacancyId);
    }


    @GetMapping("/{id}")
    public UserDto getApplicantById(@PathVariable Integer id) {
        return applicantService.getApplicantById(id);
    }


    @PostMapping("/vacancies")
    public void createVacancy(@RequestBody VacancyDto vacancyDto) {
        employerService.createVacancy(vacancyDto);
    }


    @PutMapping("/vacancies/{id}")
    public void updateVacancy(@PathVariable Integer id, @RequestBody VacancyDto vacancyDto) {
        employerService.updateVacancy(id, vacancyDto);
    }


    @DeleteMapping("/vacancies/{id}")
    public void deleteVacancy(@PathVariable Integer id) {
        employerService.deleteVacancy(id);
    }
}