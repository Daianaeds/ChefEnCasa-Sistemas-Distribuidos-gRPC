package com.grpc.grpcServer.service;

import com.grpc.grpcServer.entities.Category;
import com.grpc.grpcServer.entities.Ingredient;
import org.springframework.stereotype.Service;


public interface CategoryService {

    Category find(String name);
}
