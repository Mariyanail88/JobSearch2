package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Controller
@RequestMapping("/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    @GetMapping
    public String getResumes(Model model, Authentication authentication) {
        model.addAttribute("resumes", resumeService.getResumes());
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        if (isAuthenticated && authentication.getPrincipal() instanceof UserDetails) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            model.addAttribute("username", username);
            log.info("user logged in:  {}",username);
        }else {
            log.error("can't get user credentials:");
        }
        return "resumes/resumes";
    }

//    @GetMapping
//    public String showResumes(Model model) {
//        List<ResumeDto> resumeDtos = resumeService.getResumes();
//        List<Resume> resumes = resumeDtos.stream()
//                .map(this::convertToEntity)
//                .collect(Collectors.toList());
//        model.addAttribute("resumes", resumes);
//        return "resumes";
//    }
    private Resume convertToEntity(ResumeDto resumeDto) {
        return Resume.builder()
                .applicantId(resumeDto.getApplicantId())
                .name(resumeDto.getName())
                .categoryId(resumeDto.getCategoryId())
                .salary(resumeDto.getSalary())
                .isActive(resumeDto.getIsActive())
                .createdDate(resumeDto.getCreatedDate())
                .updateTime(resumeDto.getUpdateTime())
                .build();
    }
}