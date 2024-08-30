package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.errors.UserNotFoundException;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
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
    private final UserService userService;
    private final ResumeService resumeService;

    @GetMapping("/")
    public String welcome(Model model, Authentication authentication) throws UserNotFoundException {
        String accountType = "";
        MvcConrollersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                vacancyService.getVacancies(),
                "vacancies");
        if (authentication != null) {
            UserDto userDto = userService.getUserByEmail(authentication.getName());
            accountType = userDto.getAccountType();
        }
        model.addAttribute("accountType", accountType);
        model.addAttribute("resumes", resumeService.getResumes());
        return "index";
    }
}
