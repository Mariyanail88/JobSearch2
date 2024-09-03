package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.errors.UserNotFoundException;
import kg.attractor.jobsearch.service.CategoriesService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.MvcConrollersUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("resumes")
public class ResumeController {

    private final ResumeService resumeService;
    private final UserService userService;
    private final CategoriesService categoriesService;

    @GetMapping()
    public String getResumes(Model model, Authentication authentication) {
        MvcConrollersUtil.authCheckAndAddAttributes(
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
        MvcConrollersUtil.authCheckAndAddAttributes(
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
            List<CategoryDto> categories = categoriesService.getCategories();
            log.info("Categories: {}", categories); // Добавьте это для отладки
            model.addAttribute("categories", categories);
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

        // Проверка аутентификации
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/auth/login";
        }

        // Получение данных пользователя
        UserDto userDto = userService.getUserByEmail(authentication.getName());
        log.info("Authenticated user: {}", userDto);

        // Установка данных резюме
        resumeDto.setApplicantId(userDto.getId());
        resumeDto.setCreatedDate(LocalDateTime.now());
        resumeDto.setUpdateTime(LocalDateTime.now());

        // Проверка на ошибки валидации
        if (bindingResult.hasErrors()) {
            log.error("Validation errors: {}", bindingResult.getFieldErrors());
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("resumeDto", resumeDto);
            List<CategoryDto> categories = categoriesService.getCategories();
            model.addAttribute("categories", categories);
            return "resumes/create_resume";
        }

        try {
            // Сохранение резюме
            log.info("Creating resume: {}", resumeDto);
            ResumeDto createdResume = resumeService.addResume(resumeDto);
            log.info("Resume created successfully: {}", createdResume);

            // Перенаправление на страницу профиля с добавленным резюме
            return "redirect:/resumes/profile";
        } catch (Exception e) {
            log.error("Error creating resume", e);
            model.addAttribute("errorMessage", "An error occurred while creating the resume. Please try again.");
            List<CategoryDto> categories = categoriesService.getCategories();
            model.addAttribute("categories", categories);
            return "resumes/create_resume";
        }
    }

    @GetMapping("edit/{resumeId}")
    public String showEditResumeForm(@PathVariable Integer resumeId, Model model, Authentication authentication) {
        ResumeDto resumeDto = resumeService.getResumeById(resumeId);
        model.addAttribute("resume", resumeDto);
        MvcConrollersUtil.authCheck(model, authentication);
        return "resumes/edit_resume";
    }

    @PostMapping("edit/{resumeId}")
    public String editResume(@PathVariable Integer resumeId, @ModelAttribute("resume") ResumeDto resumeDto, Authentication authentication) {
        resumeService.updateResume(resumeId, resumeDto);
        return "redirect:/resumes";
    }

    @GetMapping("profile")
    public String getProfile(Model model, Authentication authentication) throws UserNotFoundException {
        if (authentication != null && authentication.isAuthenticated()) {
            UserDto userDto = userService.getUserByEmail(authentication.getName());
            log.info("UserDto: {}", userDto); // Добавьте это для отладки
            List<ResumeDto> resumes = resumeService.getResumesByUserId(userDto.getId());
            List<?> vacanciesUserResponded = userService.getUsersRespondedToVacancy(userDto.getId()); // Предполагаем, что этот метод существует
            log.info("VacanciesUserResponded: {}", vacanciesUserResponded); // Добавьте это для отладки
            model.addAttribute("userDto", userDto);
            model.addAttribute("userResumes", resumes); // Изменено с "resumes" на "userResumes"
            model.addAttribute("vacanciesUserResponded", vacanciesUserResponded); // Добавляем переменную в модель
            return "auth/profile";
        }
        return "redirect:/auth/login";
    }
}