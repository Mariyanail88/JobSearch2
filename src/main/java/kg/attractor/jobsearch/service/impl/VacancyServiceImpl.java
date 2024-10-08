package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.errors.VacancyNotFoundException;
import kg.attractor.jobsearch.mappers.CustomVacancyMapper;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final VacancyRepository vacancyRepository;

    @Override
    public List<VacancyDto> getVacancies() {
        List<Vacancy> vacancies = vacancyRepository.findAll();
        List<VacancyDto> dtos = new ArrayList<>();
        vacancies.forEach(e -> dtos.add(VacancyDto.builder()
                .id(e.getId())
                .name(e.getName())
                .description(e.getDescription())
                .categoryId(e.getCategoryId())
                .salary(e.getSalary())
                .expFrom(e.getExpFrom())
                .expTo(e.getExpTo())
                .isActive(e.getIsActive())
                .authorId(e.getAuthorId())
                .createdDate(e.getCreatedDate())
                .updateTime(e.getUpdateTime())
                .build()));
        return dtos;
    }

    @Override
    public VacancyDto getVacancyById(Integer id) {
        try {
            Vacancy vacancy = vacancyRepository.findById(id)
                    .orElseThrow(() -> new VacancyNotFoundException("Can't find Vacancy with id " + id));
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
        } catch (Exception e) {
            log.error("Can't find Vacancy with id {}", id);
        }
        return null;
    }

    @Override
    public void createVacancy(VacancyDto vacancyDto) {
        Vacancy vacancy = new Vacancy();
//        User.setId(userDto.getId());
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.getIsActive());
        vacancy.setAuthorId(vacancyDto.getAuthorId());
        vacancy.setCreatedDate(vacancyDto.getCreatedDate());
        vacancy.setUpdateTime(vacancyDto.getUpdateTime());

        vacancyRepository.save(vacancy);
        log.info("added vacancy {}", vacancy.getName());
    }

    @Override
    public boolean deleteVacancy(Integer id) {
        Optional<Vacancy> vacancy = vacancyRepository.findById(id);
        if (vacancy.isPresent()) {
            vacancyRepository.delete(vacancy.get());
            log.info("vacancy deleted: {}", vacancy.get().getName());
            return true;
        }
        log.info(String.format(" vacancy with id %d not found", id));
        return false;
    }

    @Override
    public List<VacancyDto> getVacanciesUserResponded(Integer userId) {
        List<Vacancy> vacancies = vacancyRepository.findVacanciesByAuthorId(userId);
        List<VacancyDto> dtos = vacancies.stream()
                .map(vacancy -> VacancyDto.builder()
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
                        .build())
                .collect(Collectors.toList());

        if (dtos.isEmpty()) {
            log.error("Can't find vacancies with user id {}", userId);
        } else {
            log.info("found vacancies with user id {}", userId);
        }
        return dtos;
    }

    @Override
    public List<VacancyDto> getVacanciesByCategoryId(Integer categoryId) {
        List<Vacancy> vacancies = vacancyRepository.findVacanciesByCategoryId(categoryId);
        List<VacancyDto> dtos = vacancies.stream()
                .map(vacancy -> VacancyDto.builder()
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
                        .build())
                .collect(Collectors.toList());

        if (dtos.isEmpty()) {
            log.error("Can't find vacancies with category id " + categoryId);
        } else {
            log.info("found vacancies with category id " + categoryId);
        }
        return dtos;
    }


    @Override
    public void editVacancy(Integer id, VacancyDto vacancyDto) {
        // Реализация метода editVacancy
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacancy not found with id " + id));

        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.getIsActive());
        vacancy.setAuthorId(vacancyDto.getAuthorId());
        vacancy.setCreatedDate(vacancyDto.getCreatedDate());
        vacancy.setUpdateTime(vacancyDto.getUpdateTime());

        vacancyRepository.save(vacancy);
        log.info("edited vacancy {}", vacancy.getName());
    }

    @Override
    public List<VacancyDto> getVacanciesByCategory(String category) {

        return List.of();
    }

    @Override
    public List<VacancyDto> getVacancyByAuthorId(Integer id) {
        List<Vacancy> vacancies = vacancyRepository.findVacanciesByAuthorId(id);
        return vacancies.stream()
                .map(CustomVacancyMapper::toDto)
                .toList();

    }

    @Override
    public void updateVacancy(Integer id, VacancyDto vacancyDto) {
        Vacancy vacancy = vacancyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vacancy not found with id " + id));

        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.getIsActive());
        vacancy.setAuthorId(vacancyDto.getAuthorId());
        vacancy.setCreatedDate(vacancyDto.getCreatedDate());
        vacancy.setUpdateTime(vacancyDto.getUpdateTime());

        vacancyRepository.save(vacancy);
        log.info("updated vacancy {}", vacancy.getName());
    }

}