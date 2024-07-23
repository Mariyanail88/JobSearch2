package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    List<VacancyDto> getAllVacancies();
    List<VacancyDto> getVacanciesByCategory(String category);
    void applyToVacancy(Integer vacancyId, UserDto userDto);
}