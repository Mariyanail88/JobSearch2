package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.errors.UserNotFoundException;
import kg.attractor.jobsearch.service.CategoriesService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.MvcControllersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("resumes")
public class ResumeController {

    private final ResumeService resumeService;
    private final UserService userService;
    private final CategoriesService categoriesService;

    @ModelAttribute
    public void addAttributes(Model model,
                              CsrfToken csrfToken,
                              @SessionAttribute(name = "currentLocale", required = false) Locale locale
    ) {

        ResourceBundle bundle = MvcControllersUtil.getResourceBundleSetLocaleSetProperties(model, locale);
        List<CategoryDto> categories = categoriesService.getCategories();
        model.addAttribute("categories", categories);
    }

    @GetMapping()
    public String getResumes(Model model, Authentication authentication) {
        MvcControllersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                resumeService.getResumesByUserId(),
                "resumes");
        return "resumes/resumes";
    }

    @GetMapping("{resumeId}")
    public String getInfo(@PathVariable Integer resumeId, Model model, Authentication authentication) throws UserNotFoundException {
        ResumeDto resumeDto = resumeService.getResumeById(resumeId);
        UserDto userDto = userService.getUserById(resumeDto.getApplicantId());
        CategoryDto categoryDto = categoriesService.getCategoryById(resumeDto.getCategoryId());
        model.addAttribute("userDto", userDto);
        model.addAttribute("categoryDto", categoryDto);
        MvcControllersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                resumeDto,
                "resume"
        );
        return "resumes/resume_info";
    }

    @GetMapping("create")
    public String showCreateResumeForm(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("resumeDto", new ResumeDto());
            return "resumes/create_resume";
        }
        return "redirect:/auth/login";
    }

    @PostMapping("create")
    public String createResume(
            @ModelAttribute("resumeDto") ResumeDto resumeDto,
            BindingResult bindingResult,
            Authentication authentication,
            Model model) throws UserNotFoundException {

        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        UserDto userDto = userService.getUserByEmail(authentication.getName());
        log.info("Authenticated user: {}", userDto);

        resumeDto.setApplicantId(userDto.getId());
        resumeDto.setCreatedDate(LocalDateTime.now());
        resumeDto.setUpdateTime(LocalDateTime.now());

        if (bindingResult.hasErrors()) {
            log.error("Validation errors: {}", bindingResult.getFieldErrors());
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("resumeDto", resumeDto);
            return "resumes/create_resume";
        }
        try {

            log.info("Creating resume: {}", resumeDto);
            ResumeDto createdResume = resumeService.addResume(resumeDto);
            log.info("Resume created successfully: {}", createdResume);

            return "redirect:/resumes/profile";
        } catch (Exception e) {
            log.error("Error creating resume", e);
            model.addAttribute("errorMessage", "An error occurred while creating the resume. Please try again.");
            return "resumes/create_resume";
        }
    }

    @GetMapping("edit/{resumeId}")
    public String showEditResumeForm(@PathVariable Integer resumeId, Model model, Authentication authentication) {
        ResumeDto resumeDto = resumeService.getResumeById(resumeId);
        model.addAttribute("resume", resumeDto);
        MvcControllersUtil.authCheck(model, authentication);
        return "resumes/edit_resume";
    }

    @PostMapping("edit/{resumeId}")
    public String editResume(@PathVariable Integer resumeId, @ModelAttribute("resume") ResumeDto resumeDto, Authentication authentication) {
        resumeService.updateResume(resumeId, resumeDto);
        return "redirect:/resumes";
    }

//    @GetMapping("profile")
//    public String getProfile(Model model, Authentication authentication) throws UserNotFoundException {
//        if (authentication != null && authentication.isAuthenticated()) {
//            UserDto userDto = userService.getUserByEmail(authentication.getName());
//            log.info("UserDto: {}", userDto); // Добавьте это для отладки
//            List<ResumeDto> resumes = resumeService.getResumesByUserId(userDto.getId());
//            List<?> vacanciesUserResponded = userService.getUsersRespondedToVacancy(userDto.getId()); // Предполагаем, что этот метод существует
//            log.info("VacanciesUserResponded: {}", vacanciesUserResponded); // Добавьте это для отладки
//            model.addAttribute("userDto", userDto);
//            model.addAttribute("userResumes", resumes); // Изменено с "resumes" на "userResumes"
//            model.addAttribute("vacanciesUserResponded", vacanciesUserResponded); // Добавляем переменную в модель
//            return "auth/profile";
//        }
//        return "redirect:/auth/login";
//    }
    @PostMapping("update/{resumeId}")
    public String updateResume(
            @PathVariable Integer resumeId,
            Authentication authentication,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (authentication != null && authentication.isAuthenticated()) {
            ResumeDto resumeDto = resumeService.getResumeById(resumeId);
            resumeService.updateResume(resumeId);

            redirectAttributes.addFlashAttribute("ifEntityUpdated", true);
            redirectAttributes.addFlashAttribute("entityTitle", "resume");
            redirectAttributes.addFlashAttribute("entityName", resumeDto.getName());
            return "redirect:/profile";
        }
        return "redirect:/auth/login";
    }
}