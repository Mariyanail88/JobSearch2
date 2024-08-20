package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.MvcConrollersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping()
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping("vacancies")
    public String getVacancies(Model model, Authentication authentication) {
        MvcConrollersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                vacancyService.getVacancies(),
                "vacancies");
        return "vacancies/vacancies";
    }

    @GetMapping("vacancies/{vacancyId}")
    public String getInfo(@PathVariable long vacancyId, Model model, Authentication authentication) {
        MvcConrollersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                vacancyService.getVacancyById(vacancyId),
                "vacancy"
        );
        return "vacancies/vacancy_info";
    }


    @GetMapping("/create")
    public String createVacancy(Model model) {
        model.addAttribute("vacancyDto", new VacancyDto());
        return "vacancies/create";
    }

    @PostMapping("/create")
    public String createVacancy(VacancyDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
        return "redirect:/vacancies";
    }
}