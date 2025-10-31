package com.swapnil.service.impl;

import com.swapnil.modal.Category;
import com.swapnil.payloaddto.SaloonDTO;
import com.swapnil.repository.Categoryrepo;
import org.springframework.beans.factory.annotation.Autowired;
import com.swapnil.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class Categoryserviceimpl implements CategoryService {


    @Autowired
    private final Categoryrepo categoryRepository;

    @Override
    public Category saveCategory(Category category, SaloonDTO salon) {

        Category newCategory=new Category();
        newCategory.setName(category.getName());
        newCategory.setImage(category.getImage());
        newCategory.setSaloonId(salon.getId());


        return categoryRepository.save(newCategory);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Set<Category> getAllCategoriesBySalon(Long salonId) {
        return categoryRepository.findBySaloonId(salonId);
    }

    @Override
    public Category getCategoryById(Long id) throws Exception {
        return categoryRepository.findById(id)
                .orElseThrow(()->new Exception("category not found"));
    }

    @Override
    public Category updateCategory(Long id,Category category) throws Exception {
        Category existingCategory=getCategoryById(id);

        if(category.getName()!=null){
            existingCategory.setName(category.getName());
        }
        if(category.getImage()!=null){
            existingCategory.setImage(category.getImage());
        }

        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        getCategoryById(id);
        categoryRepository.deleteById(id);


    }
}