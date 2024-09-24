package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> getResumesByUserId();
    ResumeDto getResumeById(Integer id);
    ResumeDto getResumeByCategoryId(Integer categoryId);
    ResumeDto addResume(ResumeDto resumeDto);
    boolean deleteResume(Integer id);
    List<ResumeDto> getResumesByUserId(Integer userId);
    void editResume(Integer id, ResumeDto resumeDto);
    List<ResumeDto> getResumeDtos(); // Новые методы
    ResumeDto createResume(ResumeDto resumeDto);
    ResumeDto updateResume(Integer id, ResumeDto resumeDto);
    void updateResume(Integer resumeId);
    List<ResumeDto> getResumesRespondedToEmployerVacancies(Integer id);
}