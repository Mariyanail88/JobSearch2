package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.ApplicantService;
import kg.attractor.jobsearch.service.EmployerService;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/applicants")
@RequiredArgsConstructor
public class ApplicantController {
    private final ApplicantService applicantService;
    private final EmployerService employerService;
    private final ResumeService resumeService;

    @GetMapping("/resumes")
    public List<UserDto> getAllResumes() {
        return applicantService.getAllResumes();
    }

//    @GetMapping("/resumes/category/{category}")
//    public List<UserDto> getResumesByCategory(@PathVariable String category) {
//        return applicantService.getResumesByCategory(category);
//    }

    @GetMapping("/resumes/{user_id}")
    public ResponseEntity<?> getResumeByUserId(@PathVariable Integer user_id) {
        List<ResumeDto> resumes = resumeService.getResumeByUserId(user_id);
        if (resumes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("resumes with user_id %d not found", user_id));
        }
        return ResponseEntity.ok(resumes);
    }
    @PostMapping("/resumes/add")
    public ResponseEntity<?> add( @RequestBody ResumeDto resumeDto) {
        resumeService.addResume(resumeDto);
        return ResponseEntity.ok("resume is valid");
    }

    @DeleteMapping("/resumes/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Integer id) {
        if (resumeService.deleteResume(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
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