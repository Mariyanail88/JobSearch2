package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.errors.UserNotFoundException;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Locale;

@AllArgsConstructor
@Controller
public class CompanyController {
    private final UserService userService;
    private final VacancyService vacancyService;

    @Autowired
    private MessageSource messageSource;

    @GetMapping("/company/{email}")
    public String getCompanyInfo(@PathVariable String email, Model model, Authentication authentication, Locale locale) throws UserNotFoundException {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto userDto = userService.getUserByEmail(email);
            List<VacancyDto> companyVacancies = vacancyService.getVacancyByAuthorId(userDto.getId());

            model.addAttribute("userDto", userDto);
            model.addAttribute("companyVacancies", companyVacancies);
            model.addAttribute("successMessage", messageSource.getMessage("company.info.success", null, locale));

            return "/vacancies/company-info";
        }

        model.addAttribute("errorMessage", messageSource.getMessage("company.info.error.notAuthenticated", null, locale));
        return "redirect:/auth/login";
    }
}