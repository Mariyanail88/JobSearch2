package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.errors.EntityNotFoundException;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class VacancyServiceImpl implements VacancyService {

    private final List<VacancyDto> vacancies = new ArrayList<>();
    private final VacancyDao vacancyDao;

    public VacancyServiceImpl(VacancyDao vacancyDao) {
        this.vacancyDao = vacancyDao;
    }
    @Override
    public List<VacancyDto> getVacancies() {
        return new ArrayList<>(vacancies);
    }

    @Override
    public List<VacancyDto> getVacancyDtos() {
        var vacancies = vacancyDao.getVacancies();
        return vacancies.stream()
                .map(this::convertToDto)
                .toList();
    }
    private VacancyDto convertToDto(Vacancy vacancy) {
        return VacancyDto.builder()
                .id(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .categoryId(vacancy.getCategoryId())
                .salary(vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .authorId(vacancy.getAuthorId())
                .createdDate(vacancy.getCreatedDate())
                .updateTime(vacancy.getUpdateTime())
                .build();
    }



    @Override
    public VacancyDto getVacancyById(Integer id) {
        return vacancies.stream()
                .filter(vacancy -> vacancy.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Vacancy not found with id: " + id));
    }

    @Override
    public void createVacancy(VacancyDto vacancyDto) {
        vacancies.add(vacancyDto);
    }

    @Override
    public boolean deleteVacancy(Integer id) {
        return vacancies.removeIf(vacancy -> vacancy.getId().equals(id));
    }

    @Override
    public List<VacancyDto> getVacanciesUserResponded(Integer userId) {
        return null;
    }

    @Override
    public List<VacancyDto> getVacanciesByCategoryId(Integer categoryId) {
        return null;
    }

    @Override
    public void editVacancy(Integer id, VacancyDto vacancyDto) {
        VacancyDto existingVacancy = getVacancyById(id);
        existingVacancy.setName(vacancyDto.getName());
        existingVacancy.setDescription(vacancyDto.getDescription());
        existingVacancy.setCategoryId(vacancyDto.getCategoryId());
        existingVacancy.setSalary(vacancyDto.getSalary());
        existingVacancy.setExpFrom(vacancyDto.getExpFrom());
        existingVacancy.setExpTo(vacancyDto.getExpTo());
        existingVacancy.setIsActive(vacancyDto.getIsActive());
        existingVacancy.setAuthorId(vacancyDto.getAuthorId());
        existingVacancy.setCreatedDate(vacancyDto.getCreatedDate());
        existingVacancy.setUpdateTime(vacancyDto.getUpdateTime());
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(String category) {
        return null;
    }

    @Override
    public void applyToVacancy(Integer vacancyId, UserDto userDto) {
    }

    @Override
    public List<VacancyDto> getAllVacancies() {
        return new ArrayList<>(vacancies);
    }
}