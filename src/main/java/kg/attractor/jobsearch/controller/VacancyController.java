package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.errors.UserNotFoundException;
import kg.attractor.jobsearch.service.CategoriesService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Locale;
import java.util.ResourceBundle;

@Controller
@Slf4j
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final UserService userService;
    private final CategoriesService categoriesService;

    @ModelAttribute
    public void addAttributes(Model model,
                              CsrfToken csrfToken,
                              @SessionAttribute(name = "currentLocale", required = false) Locale locale) {
        ResourceBundle bundle = MvcControllersUtil.getResourceBundleSetLocaleSetProperties(model, locale);
    }

    @GetMapping()
    public String getVacancies(Model model, Authentication authentication) {
        MvcControllersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                vacancyService.getVacancies(),
                "vacancies");
        return "vacancies/vacancies";
    }

    @GetMapping("{vacancyId}")
    public String getInfo(@PathVariable Integer vacancyId, Model model, Authentication authentication) throws UserNotFoundException {
        VacancyDto vacancy = vacancyService.getVacancyById(vacancyId);
        UserDto userDto = userService.getUserById(vacancy.getAuthorId());
        CategoryDto categoryDto = categoriesService.getCategoryById(vacancy.getCategoryId());

        model.addAttribute("user", userDto);
        model.addAttribute("category", categoryDto);
        MvcControllersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                vacancy,
                "vacancy"
        );
        return "vacancies/vacancy_info";
    }

    @GetMapping("create")
    public String createVacancy(Model model) {
        model.addAttribute("vacancyDto", new VacancyDto());
        return "vacancies/create_vacancy";
    }

    @PostMapping("create")
    public String createVacancy(VacancyDto vacancyDto) {
        vacancyService.createVacancy(vacancyDto);
        return "redirect:/vacancies";
    }

    @PostMapping()
    public String createVacancy(@ModelAttribute("vacancy") VacancyDto vacancyDto, Authentication authentication) {
        vacancyService.createVacancy(vacancyDto);
        return "redirect:/vacancies";
    }

    @GetMapping("edit/{vacancyId}")
    public String showEditVacancyForm(@PathVariable Integer vacancyId, Model model, Authentication authentication) {
        VacancyDto vacancyDto = vacancyService.getVacancyById(vacancyId);
        model.addAttribute("vacancy", vacancyDto);
        MvcControllersUtil.authCheck(model, authentication);
        return "vacancies/edit_vacancy";
    }

    @PostMapping("edit/{vacancyId}")
    public String editVacancy(@PathVariable Integer vacancyId, @ModelAttribute("vacancy") VacancyDto vacancyDto, Authentication authentication) {
        vacancyService.updateVacancy(vacancyId, vacancyDto);
        return "redirect:/vacancies";
    }
}