package kg.attractor.jobsearch.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.errors.ResourceNotFoundException;
import kg.attractor.jobsearch.mappers.CustomResumeMapper;
import kg.attractor.jobsearch.mappers.ResumeMapper;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {


    private final ResumeRepository resumeRepository;
    private final ResumeMapper resumeMapper;

    @Override
    public List<ResumeDto> getResumesByUserId() {
        List<Resume> resumes = resumeRepository.findAll();
        return resumes.stream()
                .map(CustomResumeMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResumeDto getResumeById(Integer id) {
        Optional<Resume> resume = resumeRepository.findById(id);
        if (resume.isEmpty()) {
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }
        return CustomResumeMapper.convertToDto(resume.get());
    }

    @Override
    public ResumeDto getResumeByCategoryId(Integer categoryId) {
        List<Resume> resumes = resumeRepository.findAll();
        if (resumes.isEmpty()) {
            throw new ResourceNotFoundException("No resumes found for category id: " + categoryId);
        }
        return CustomResumeMapper.convertToDto(resumes.get(0)); // Возвращаем первый найденный резюме
    }

    @Override
    @Transactional
    public ResumeDto addResume(ResumeDto resumeDto) {
        try {
            log.info("Saving resume: {}", resumeDto);
            Resume resume = Resume.builder()
                    .applicantId(resumeDto.getApplicantId())
                    .name(resumeDto.getName())
                    .categoryId(resumeDto.getCategoryId())
                    .salary(resumeDto.getSalary())
                    .isActive(resumeDto.getIsActive())
                    .createdDate(resumeDto.getCreatedDate())
                    .updateTime(resumeDto.getUpdateTime())
                    .build();
            resumeRepository.save(resume);
            log.info("Resume saved successfully: {}", resume);
        } catch (Exception e) {
            log.error("Error saving resume: {}", resumeDto, e);
            throw e;
        }
        return resumeDto;
    }

    @Override
    public boolean deleteResume(Integer id) {
        Optional<Resume> resume = resumeRepository.findById(id);
        if (resume.isEmpty()) {
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }
        resumeRepository.delete(resume.get());
        return true;
    }

    @Override
    public List<ResumeDto> getResumesByUserId(Integer userId) {
        List<Resume> resumes = resumeRepository.findByApplicantId(userId);
        return resumes.stream()
                .map(CustomResumeMapper::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void editResume(Integer id, ResumeDto resumeDto) {
        Optional<Resume> existingResume = resumeRepository.findById(id);
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

       resumeRepository.save(resume);
    }

    @Override
    public List<ResumeDto> getResumeDtos() {
        var resumes = resumeRepository.findAll();
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

    @Override
    public ResumeDto createResume(ResumeDto resumeDto) {
        Resume resume = Resume.builder()
                .applicantId(resumeDto.getApplicantId())
                .name(resumeDto.getName())
                .categoryId(resumeDto.getCategoryId())
                .salary(resumeDto.getSalary())
                .isActive(resumeDto.getIsActive())
                .createdDate(LocalDateTime.now()) // Устанавливаем текущую дату и время
                .updateTime(LocalDateTime.now()) // Устанавливаем текущую дату и время
                .build();
        resumeRepository.save(resume);
        return convertToDto(resume);
    }

    @Override
    public ResumeDto updateResume(Integer id, ResumeDto resumeDto) {
        resumeDto.setUpdateTime(LocalDateTime.now());
        Optional<Resume> existingResume = resumeRepository.findById(id);
        if (existingResume.isEmpty()) {
            throw new ResourceNotFoundException("Resume not found with id: " + id);
        }

        Resume resume = existingResume.get();
        resume.setId(existingResume.get().getId());
        resume.setApplicantId(resumeDto.getApplicantId());
        resume.setName(resumeDto.getName());
        resume.setCategoryId(resumeDto.getCategoryId());
        resume.setSalary(resumeDto.getSalary());
        resume.setIsActive(resumeDto.getIsActive());
        resume.setCreatedDate(existingResume.get().getCreatedDate());
        resume.setUpdateTime(resumeDto.getUpdateTime());

        resumeRepository.save(resume);
        return convertToDto(resume);
    }

    @Override
    public List<ResumeDto> getResumesRespondedToEmployerVacancies(Integer userId) {
        return resumeRepository.findResumesRespondedToEmployerVacancies(userId)
                .stream()
                .map(resumeMapper::toResumeDto)
                .toList();
    }
    @Override
    public void updateResume(Integer resumeId) {
        ResumeDto existingResume = getResumeById(resumeId);
        existingResume.setUpdateTime(LocalDateTime.now());
        Resume resume = resumeMapper.toResume(existingResume);
        resumeRepository.save(resume);
        log.info("updated resumes {}", resume.getName());
    }
}


