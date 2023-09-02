package com.grpc.grpcServer.service;

import com.grpc.grpServer.UserAuth;
import com.grpc.grpServer.UserBasic;
import com.grpc.grpServer.UserListRequest;
import com.grpc.grpServer.UserRequest;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;

import java.util.List;

public interface UserService {

    int save(UserRequest request) throws Exception;
    List<UserBasic> findAll(UserListRequest request) throws Exception;
    UserBasic auth(UserAuth auth) throws Exception;

    User find(UserAuth auth);

    void addRecipe(Recipe recipe, UserAuth auth);
}
