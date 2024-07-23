package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.EmployerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployerServiceImpl implements EmployerService {

    @Override
    public void createVacancy(VacancyDto vacancyDto) {

    }

    @Override
    public void updateVacancy(Integer id, VacancyDto vacancyDto) {

    }

    @Override
    public void deleteVacancy(Integer id) {

    }

    @Override
    public List<VacancyDto> getAllVacancies() {

        return null;
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(String category) {

        return null;
    }
}