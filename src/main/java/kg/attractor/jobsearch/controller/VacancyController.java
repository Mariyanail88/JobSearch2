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
import org.springframework.web.bind.annotation.ModelAttribute;
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
        return "vacancies/create_vacancy";
    }

    @PostMapping("/create")
    public String createVacancy(VacancyDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
        return "redirect:/vacancies";
    }

    // Метод для обработки создания вакансии
    @PostMapping("vacancies")
    public String createVacancy(@ModelAttribute("vacancy") VacancyDto vacancyDto, Authentication authentication) {
        vacancyService.createVacancy(vacancyDto);
        return "redirect:/vacancies";
    }

    // Метод для отображения формы редактирования вакансии
    @GetMapping("vacancies/edit/{vacancyId}")
    public String showEditVacancyForm(@PathVariable Integer vacancyId, Model model, Authentication authentication) {
        VacancyDto vacancyDto = vacancyService.getVacancyById(vacancyId);
        model.addAttribute("vacancy", vacancyDto);
        MvcConrollersUtil.authCheck(model, authentication);
        return "vacancies/edit_vacancy";
    }

    // Метод для обработки редактирования вакансии
    @PostMapping("vacancies/edit/{vacancyId}")
    public String editVacancy(@PathVariable Integer vacancyId, @ModelAttribute("vacancy") VacancyDto vacancyDto, Authentication authentication) {
        vacancyService.updateVacancy(vacancyId, vacancyDto);
        return "redirect:/vacancies";
    }

}