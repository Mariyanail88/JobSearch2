package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.errors.CategoryNotFoundException;
import kg.attractor.jobsearch.mappers.CategoryMapper;
import kg.attractor.jobsearch.mappers.CustomCategoryMapper;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.repository.CategoryRepository;
import kg.attractor.jobsearch.service.CategoriesService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class CategoriesServiceImpl implements CategoriesService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Override
    public List<CategoryDto> getCategories() {
        return categoryRepository
                .findAll()
                .stream()
                // using custom mapper or id = null in CategoryDto
                .map(CustomCategoryMapper::toDto)
                .toList();
    }

    @Override
    public CategoryDto getCategoryById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("category with id {} not found", id);
                    return new CategoryNotFoundException(String.format("category with id %d not found", id));
                });
        return CustomCategoryMapper.toDto(category);
    }

}



