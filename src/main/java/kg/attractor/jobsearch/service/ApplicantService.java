package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;

import java.util.List;

public interface ApplicantService {
    List<ResumeDto> getAllResumes();

    List<UserDto> getResumesByCategory(String category);

    List<UserDto> getApplicantsByVacancyId(Integer vacancyId);

    UserDto getApplicantById(Integer id);
}
