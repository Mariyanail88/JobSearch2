package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping()
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping
    public String getVacancies(Model model, Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("vacancies", vacancyService.getVacancies());
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("username", username);
            log.info("user logged in:  {}",username);
        }else {
            log.error("can't get user credentials:");
        }
        return "vacancies/index";
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