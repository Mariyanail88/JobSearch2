package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacancyServiceImpl implements VacancyService {

    @Override
    public List<VacancyDto> getAllVacancies() {

        return null;
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(String category) {

        return null;
    }

    @Override
    public void applyToVacancy(Integer vacancyId, UserDto userDto) {

    }
}