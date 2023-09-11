package com.grpc.grpcServer.service;

import com.grpc.grpcServer.ResponseUsernameAndEmailList;
import com.grpc.grpcServer.UserAuth;
import com.grpc.grpcServer.UserBasic;
import com.grpc.grpcServer.UserRequest;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;

import java.util.List;

public interface UserService {

    int save(UserRequest request) throws Exception;

    User saveUser(User user) throws Exception;

    ResponseUsernameAndEmailList findAll() throws Exception;

    UserBasic auth(UserAuth auth) throws Exception;

    User find(UserAuth auth);

    User findByUsername(String username);

    void addRecipe(Recipe recipe, UserAuth auth);

    String followUser(String favouriteUser, String username);

    String unfollowUser(String favouriteUser, String username);

    ResponseUsernameAndEmailList getFavouriteUsers(String username);

    List<Recipe> getFavouriteRecipes(String username);
}
