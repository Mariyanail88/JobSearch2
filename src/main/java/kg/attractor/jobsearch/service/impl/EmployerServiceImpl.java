package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.errors.ResourceNotFoundException;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.EmployerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployerServiceImpl implements EmployerService {

    private final VacancyDao vacancyDao;

    @Override
    public void createVacancy(VacancyDto vacancyDto) {
        if (vacancyDto.getName() == null || vacancyDto.getName().isEmpty()) {
            throw new IllegalArgumentException("Vacancy name is required");
        }
        if (vacancyDto.getCategoryId() == null) {
            throw new IllegalArgumentException("Category ID is required");
        }
        if (vacancyDto.getAuthorId() == null) {
            throw new IllegalArgumentException("Author ID is required");
        }

        Vacancy vacancy = Vacancy.builder()
                .name(vacancyDto.getName())
                .description(vacancyDto.getDescription())
                .categoryId(vacancyDto.getCategoryId())
                .salary(vacancyDto.getSalary())
                .expFrom(vacancyDto.getExpFrom())
                .expTo(vacancyDto.getExpTo())
                .isActive(vacancyDto.getIsActive())
                .authorId(vacancyDto.getAuthorId())
                .createdDate(vacancyDto.getCreatedDate())
                .updateTime(vacancyDto.getUpdateTime())
                .build();
        vacancyDao.save(vacancy);
    }

    @Override
    public void updateVacancy(Integer id, VacancyDto vacancyDto) {
        Optional<Vacancy> existingVacancy = vacancyDao.getVacancyById(id);
        if (existingVacancy.isEmpty()) {
            throw new ResourceNotFoundException("Vacancy not found with id: " + id);
        }

        Vacancy vacancy = existingVacancy.get();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.getIsActive());
        vacancy.setAuthorId(vacancyDto.getAuthorId());
        vacancy.setUpdateTime(vacancyDto.getUpdateTime());

        vacancyDao.update(vacancy);
    }

    @Override
    public void deleteVacancy(Integer id) {
        Optional<Vacancy> existingVacancy = vacancyDao.getVacancyById(id);
        if (existingVacancy.isEmpty()) {
            throw new ResourceNotFoundException("Vacancy not found with id: " + id);
        }

        vacancyDao.delete(id);
    }

    @Override
    public List<VacancyDto> getAllVacancies() {
        List<Vacancy> vacancies = vacancyDao.getAllVacancies();
        return vacancies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(Integer category) {
        List<Vacancy> vacancies = vacancyDao.getVacanciesByCategory(category);
        return vacancies.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Метод для преобразования Vacancy в VacancyDto
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
}