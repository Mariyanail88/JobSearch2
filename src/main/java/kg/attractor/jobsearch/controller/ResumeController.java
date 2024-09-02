package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.errors.UserNotFoundException;
import kg.attractor.jobsearch.service.CategoriesService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.impl.CustomUserDetails;
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
        model.addAttribute("resume", new ResumeDto());
        MvcConrollersUtil.authCheck(model, authentication);

        // Получение списка категорий и добавление его в модель
        List<CategoryDto> categories = categoriesService.getCategories();
        model.addAttribute("categories", categories);

        return "resumes/create_resume";
    }


    @PostMapping("create")
    public String createResume(@ModelAttribute ResumeDto resumeDto, Authentication authentication) {
        // Получение ID Соискателя из аутентификационных данных
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        int applicantId = getApplicantIdFromUserDetails(userDetails);

        // Установка ID Соискателя в DTO
        resumeDto.setApplicantId(applicantId);

        // Логика сохранения резюме
        resumeService.createResume(resumeDto);

        // Перенаправление на профиль создателя
        return "redirect:/auth/profile/" + applicantId;
    }

    private int getApplicantIdFromUserDetails(UserDetails userDetails) {
        if (userDetails instanceof CustomUserDetails) {
            return ((CustomUserDetails) userDetails).getApplicantId();
        }
        throw new IllegalArgumentException("UserDetails не содержит информацию о соискателе");
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


}