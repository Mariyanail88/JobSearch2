package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicants")
@RequiredArgsConstructor
public class ApplicantController {
    private final ApplicantService applicantService;

    @GetMapping
    public List<UserDto> getAllApplicants() {
        return applicantService.getAllResumes();
    }

    @GetMapping("/{id}")
    public UserDto getApplicantById(@PathVariable Integer id) {
        return applicantService.getApplicantById(id);
    }

    @GetMapping("/vacancy/{vacancyId}/applicants")
    public List<UserDto> getApplicantsByVacancyId(@PathVariable Integer vacancyId) {
        return applicantService.getApplicantsByVacancyId(vacancyId);
    }
}