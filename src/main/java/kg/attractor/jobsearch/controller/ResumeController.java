package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ResumeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/resumes")
public class ResumeController {

    private final ResumeService resumeService;

    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    @GetMapping
    public String getResumes(Model model) {
        model.addAttribute("resumes", resumeService.getResumes());
        return "resumes";
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