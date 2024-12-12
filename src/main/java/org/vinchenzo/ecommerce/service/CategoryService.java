package org.vinchenzo.ecommerce.service;

import org.vinchenzo.ecommerce.Payload.CategoryDTO;
import org.vinchenzo.ecommerce.Payload.CategoryResponse;


public interface CategoryService {

    CategoryResponse findAll(Integer pageNumber, Integer pageSize,String sortBy,String sortOrder);

    CategoryDTO addCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);
}
