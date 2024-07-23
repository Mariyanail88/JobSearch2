package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.ApplicantService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicantServiceImpl implements ApplicantService {

    @Override
    public List<UserDto> getAllResumes() {

        return null;
    }

    @Override
    public List<UserDto> getResumesByCategory(String category) {

        return null;
    }

    @Override
    public List<UserDto> getApplicantsByVacancyId(Integer vacancyId) {

        return null;
    }

    @Override
    public UserDto getApplicantById(Integer id) {

        return null;
    }
}