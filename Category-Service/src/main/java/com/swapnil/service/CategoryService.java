package com.swapnil.service;

import com.swapnil.modal.Category;
import com.swapnil.payloaddto.SaloonDTO;

import java.util.List;
import java.util.Set;

public interface CategoryService {


    // Create or Update a Category
    Category saveCategory(Category category, SaloonDTO salon);

    // Get all Categories
    List<Category> getAllCategories();

    Set<Category> getAllCategoriesBySalon(Long id);

    // Get Category by ID
    Category getCategoryById(Long id) throws Exception;

    Category updateCategory(Long id,Category category) throws Exception;

    // Delete Category by ID
    void deleteCategory(Long id) throws Exception;
}



