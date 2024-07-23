package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.List;

public interface EmployerService {

    void createVacancy(VacancyDto vacancyDto);


    void updateVacancy(Integer id, VacancyDto vacancyDto);


    void deleteVacancy(Integer id);

    List<VacancyDto> getAllVacancies();


    List<VacancyDto> getVacanciesByCategory(String category);
}