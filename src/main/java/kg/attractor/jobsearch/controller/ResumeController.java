/*
package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/resumes")
@RequiredArgsConstructor
@Validated
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping
    public List<ResumeDto> getAllResumes() {
        return resumeService.getResumes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeDto> getResumeById(@PathVariable Integer id) {
        ResumeDto resume = resumeService.getResumeById(id);
        if (resume == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(resume);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ResumeDto>> getResumeByUserId(@PathVariable Integer userId) {
        List<ResumeDto> resumes = resumeService.getResumeByUserId(userId);
        if (resumes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(resumes);
    }

    @PostMapping
    public ResponseEntity<Void> addResume(@Valid @RequestBody ResumeDto resumeDto) {
        resumeService.addResume(resumeDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateResume(@PathVariable Integer id, @Valid @RequestBody ResumeDto resumeDto) {
        resumeService.editResume(id, resumeDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResume(@PathVariable Integer id) {
        if (resumeService.deleteResume(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}*/
