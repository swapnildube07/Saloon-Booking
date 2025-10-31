package com.swapnil.controller;

import com.swapnil.modal.Category;
import com.swapnil.payloaddto.SaloonDTO;
import com.swapnil.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories/salon-owner")
@RequiredArgsConstructor
public class SalonCategoryController {

    private final CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        SaloonDTO saloonDTO = new SaloonDTO();
        saloonDTO.setId(1L);
        saloonDTO.setName(category.getName());
        Category savedCategories = categoryService.saveCategory(category, saloonDTO);
        return ResponseEntity.ok(savedCategories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) throws Exception {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully!");
    }
}
