package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.mappers.ResumeMapper;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ApplicantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {
    private final ResumeDao resumeDao;



    @Override
    public List<ResumeDto> getAllResumes() {
        List<Resume> resumes = resumeDao.getAllResumes();
        return resumes.stream()
                .map(ResumeMapper::convertToDto)
                .collect(Collectors.toList());

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