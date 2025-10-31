package com.swapnil.controller;


import com.swapnil.modal.Category;
import com.swapnil.payloaddto.SaloonDTO;
import com.swapnil.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class Categorycontroller {


    private final CategoryService categoryService;

    @GetMapping("/saloon/{id}")
    public  ResponseEntity<Set<Category>> getCategoriesBySalon(
            @PathVariable Long id
    ){
        Set<Category> categories = categoryService.getAllCategoriesBySalon(id);
            return ResponseEntity.ok(categories);
        }



    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





}
