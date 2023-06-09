package com.springboot.blog.service.impl;

import com.springboot.blog.DTO.CategoryDto;
import com.springboot.blog.entity.Category;
import com.springboot.blog.exception.ResourceNotFoundException;
import com.springboot.blog.repository.CategoryRepository;
import com.springboot.blog.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Category", "id", id));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(x -> modelMapper.map(x, CategoryDto.class)).
                collect(Collectors.toList());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
        Category category  = categoryRepository.findById(categoryId).orElseThrow(()->
                new ResourceNotFoundException("Category", "id", categoryId));
         category.setName(categoryDto.getName());
         category.setDescription(categoryDto.getDescription());
         category.setId(categoryId);

         Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).
                orElseThrow(() -> new ResourceNotFoundException("Category", "id", categoryId));
        categoryRepository.delete(category);
    }
}
