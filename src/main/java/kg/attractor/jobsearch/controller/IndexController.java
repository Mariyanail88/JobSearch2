package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.MvcConrollersUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class IndexController {
    private final VacancyService vacancyService;
    @GetMapping("/")
    public String welcome(Model model, Authentication authentication) {
        MvcConrollersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                vacancyService.getVacancies(),
                "vacancies");
        return "index";
    }
}
