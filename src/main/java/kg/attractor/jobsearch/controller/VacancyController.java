package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final EmployerService employerService;

    @PostMapping
    public void createVacancy(@RequestBody VacancyDto vacancyDto) {
        employerService.createVacancy(vacancyDto);
    }

    @PutMapping("/{id}")
    public void updateVacancy(@PathVariable Integer id, @RequestBody VacancyDto vacancyDto) {
        employerService.updateVacancy(id, vacancyDto);
    }

    @DeleteMapping("/{id}")
    public void deleteVacancy(@PathVariable Integer id) {
        employerService.deleteVacancy(id);
    }
}