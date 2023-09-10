package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.entities.Category;
import com.grpc.grpcServer.entities.Ingredient;
import com.grpc.grpcServer.repositories.CategoryRepository;
import com.grpc.grpcServer.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServicesImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category find(String name) {
      Category category =  categoryRepository.findByNameCategory(name);
        if(category == null){
            category = save(name.toLowerCase());
        }
      return category;
    }

    private Category save(String name){
        Category category = Category.builder()
                .nameCategory(name)
                .build();

        return categoryRepository.save(category);
    }
}
