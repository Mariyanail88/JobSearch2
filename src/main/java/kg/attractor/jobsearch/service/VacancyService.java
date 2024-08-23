package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> getVacancies();

    VacancyDto getVacancyById(Integer id);

    void createVacancy(VacancyDto vacancyDto);

    boolean deleteVacancy(Integer id);

    List<VacancyDto> getVacanciesUserResponded(Integer userId);

    List<VacancyDto> getVacanciesByCategoryId(Integer categoryId);

    void editVacancy(Integer id, VacancyDto vacancyDto);

    List<VacancyDto> getVacanciesByCategory(String category);

    List<VacancyDto> getVacancyByAuthorId(Integer id);

    // Новый метод для обновления вакансии
    void updateVacancy(Integer id, VacancyDto vacancyDto);

}