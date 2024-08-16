package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.mappers.ResumeMapper;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.errors.ResourceNotFoundException;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeDao resumeDao;

    @Override
    public List<ResumeDto> getResumes() {
        List<Resume> resumes = resumeDao.getAllResumes();
        return resumes.stream()
                .map(ResumeMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResumeDto getResumeById(Integer id) {
        Optional<Resume> resume = resumeDao.getResumeById(id);
        if (resume.isEmpty()) {
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }
        return ResumeMapper. convertToDto(resume.get());
    }

    @Override
    public ResumeDto getResumeByCategoryId(Integer categoryId) {
        List<Resume> resumes = resumeDao.getResumesByCategoryId(categoryId);
        if (resumes.isEmpty()) {
            throw new ResourceNotFoundException("No resumes found for category id: " + categoryId);
        }
        return ResumeMapper. convertToDto(resumes.get(0)); // Возвращаем первый найденный резюме
    }

    @Override
    public void addResume(ResumeDto resumeDto) {
        Resume resume = Resume.builder()
                .applicantId(resumeDto.getApplicantId())
                .name(resumeDto.getName())
                .categoryId(resumeDto.getCategoryId())
                .salary(resumeDto.getSalary())
                .isActive(resumeDto.getIsActive())
                .createdDate(resumeDto.getCreatedDate())
                .updateTime(resumeDto.getUpdateTime())
                .build();
        resumeDao.save(resume);
    }

    @Override
    public boolean deleteResume(Integer id) {
        Optional<Resume> resume = resumeDao.getResumeById(id);
        if (resume.isEmpty()) {
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }
        resumeDao.delete(id);
        return true;
    }

    @Override
    public List<ResumeDto> getResumeByUserId(Integer userId) {
        List<Resume> resumes = resumeDao.getResumesByUserId(userId);
        return resumes.stream()
                .map(ResumeMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void editResume(Integer id, ResumeDto resumeDto) {
        Optional<Resume> existingResume = resumeDao.getResumeById(id);
        if (existingResume.isEmpty()) {
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }

        Resume resume = existingResume.get();
        resume.setApplicantId(resumeDto.getApplicantId());
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.getIsActive());
        resume.setCreatedDate(resumeDto.getCreatedDate());
        resume.setUpdateTime(resumeDto.getUpdateTime());

        resumeDao.update(resume);
    }

    @Override
    public List<ResumeDto> getResumeDtos() {
        var resumes = resumeDao.getAllResumes();
        return resumes.stream()
                .map(this::convertToDto)
                .toList();
    }

    private ResumeDto convertToDto(Resume resume) {
        return ResumeDto.builder()
                .applicantId(resume.getApplicantId())
                .name(resume.getName())
                .categoryId(resume.getCategoryId())
                .salary(resume.getSalary())
                .isActive(resume.getIsActive())
                .createdDate(resume.getCreatedDate())
                .updateTime(resume.getUpdateTime())
                .build();
    }


}