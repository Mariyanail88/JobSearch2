package kg.attractor.jobsearch.controller.api;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.ApplicantService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/applicants")
@RequiredArgsConstructor
public class ApplicantController {
    private final ApplicantService applicantService;
    private final   VacancyService vacancyService;
    private final ResumeService resumeService;
    private final UserService userService;

    @GetMapping("/resumes")
    public List<ResumeDto> getAllResumes() {
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
    @GetMapping("/vacancies")
    public ResponseEntity<List<VacancyDto>> getAllActiveVacancies() {
        return ResponseEntity.ok(vacancyService.getVacancies());
    }
    @GetMapping("/vacancies/category/{category}")
    public ResponseEntity<List<?>> getVacanciesByCategory(@PathVariable String category) {
        return ResponseEntity.ok(vacancyService.getVacanciesByCategory(category));
    }
    @GetMapping("get-user-resumes/{user_id}")
    public ResponseEntity<?> getResumesByUserId(@PathVariable Integer user_id) {
        List<ResumeDto> resumes = resumeService.getResumesByUserId(user_id);
        if (resumes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("resumes with user_id %d not found", user_id));
        }
        return ResponseEntity.ok(resumes);
    }
    @GetMapping("/resume/{id}")
    public ResponseEntity<ResumeDto> getResumeById(@PathVariable Integer id) {
        ResumeDto resume = resumeService.getResumeById(id);
        if (resume == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(resume);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResumeDto>> getResumeByUserId(@PathVariable Integer userId) {
        List<ResumeDto> resumes = resumeService.getResumesByUserId(userId);
        if (resumes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(resumes);
    }
    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody UserDto user) {
        user.setAccountType("applicant");
        userService.addUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/resume")
    public ResponseEntity<Void> addResume(@Valid @RequestBody ResumeDto resumeDto) {
        resumeService.addResume(resumeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/resume/{id}")
    public ResponseEntity<Void> updateResume(@PathVariable Integer id, @Valid @RequestBody ResumeDto resumeDto) {
        resumeService.editResume(id, resumeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/resume/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Integer id) {
        if (resumeService.deleteResume(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}