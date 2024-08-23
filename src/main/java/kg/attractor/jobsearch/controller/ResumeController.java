package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.util.MvcConrollersUtil;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Controller
@RequestMapping()
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    @GetMapping("resumes")
    public String getResumes(Model model, Authentication authentication) {
        MvcConrollersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                resumeService.getResumes(),
                "resumes");
        return "resumes/resumes";
    }

    @GetMapping("resumes/{resumeId}")
    public String getInfo(@PathVariable Integer resumeId, Model model, Authentication authentication) {
        MvcConrollersUtil.authCheckAndAddAttributes(
                model,
                authentication,
                resumeService.getResumeById(resumeId),
                "resume"
        );
        return "resumes/resume_info";
    }
    // Метод для отображения формы создания резюме
    @GetMapping("resumes/create")
    public String showCreateResumeForm(Model model, Authentication authentication) {
        model.addAttribute("resume", new ResumeDto());
        MvcConrollersUtil.authCheck(model, authentication);
        return "resumes/create_resume";
    }

    // Метод для обработки создания резюме
    @PostMapping("resumes/create")
    public String createResume(@ModelAttribute("resume") ResumeDto resumeDto, Authentication authentication) {
        resumeService.createResume(resumeDto);
        return "redirect:/auth/profile";
    }
    // Метод для отображения формы редактирования резюме
    @GetMapping("resumes/edit/{resumeId}")
    public String showEditResumeForm(@PathVariable Integer resumeId, Model model, Authentication authentication) {
        ResumeDto resumeDto = resumeService.getResumeById(resumeId);
        model.addAttribute("resume", resumeDto);
        MvcConrollersUtil.authCheck(model, authentication);
        return "resumes/edit_resume";
    }
    // Метод для обработки редактирования резюме
    @PostMapping("resumes/edit/{resumeId}")
    public String editResume(@PathVariable Integer resumeId, @ModelAttribute("resume") ResumeDto resumeDto, Authentication authentication) {

        resumeService.updateResume(resumeId, resumeDto);
        return "redirect:/resumes";
    }



}