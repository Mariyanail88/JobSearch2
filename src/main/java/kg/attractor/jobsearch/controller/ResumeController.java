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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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



}