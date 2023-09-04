package com.grpc.grpcServer.service;

import com.grpc.grpcServer.*;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

@GrpcService
public class ServiceImpl extends ServiceGrpc.ServiceImplBase {

    @Autowired
    UserService userService;

    @Autowired
    RecipesService recipesService;

    @Override
    public void newUser(UserRequest request, StreamObserver<UserResponse> responseObserver) {

        try {
            int id = userService.save(request);
            UserResponse response = UserResponse.newBuilder()
                    .setId(id)
                    .setUsername(request.getUsername())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            UserResponse response = UserResponse.newBuilder()
                    .setError(e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void authentication(UserAuth auth, StreamObserver<UserBasic> responseObserver) {
        try {
            UserBasic userBasic = userService.auth(auth);
            responseObserver.onNext(userBasic);
            responseObserver.onCompleted();

        } catch (Exception e) {
            UserBasic response = UserBasic.newBuilder()
                    .setError(e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void listUser(Empty request, StreamObserver<ResponseUsernameAndEmailList> responseObserver) {

        try {
            ResponseUsernameAndEmailList users = userService.findAll();

            responseObserver.onNext(users);
            responseObserver.onCompleted();

        } catch (Exception e) {
            ResponseUsernameAndEmailList response = ResponseUsernameAndEmailList.newBuilder()
                    .setError(e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void newRecipe(RecipeRequest request, StreamObserver<RecipeResponse> responseObserver) {

        try {
            RecipeResponse response = recipesService.newRecipe(request);

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception e) {
            RecipeResponse response = RecipeResponse.newBuilder()
                    .setError(e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Transactional
    @Override
    public void followUser(UserAndFavouriteUser request, StreamObserver<ResponseOrRequestString> responseObserver) {
        String responseUser = userService.followUser(request.getFavouriteUsername(), request.getUsername());
        ResponseOrRequestString response = ResponseOrRequestString.newBuilder()
                .setRequestOrResponse(responseUser)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Transactional
    @Override
    public void unfollowUser(UserAndFavouriteUser request, StreamObserver<ResponseOrRequestString> responseObserver) {
        String responseUser = userService.unfollowUser(request.getFavouriteUsername(), request.getUsername());
        ResponseOrRequestString response = ResponseOrRequestString.newBuilder()
                .setRequestOrResponse(responseUser)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Transactional
    @Override
    public void favouriteUsers(ResponseOrRequestString request, StreamObserver<ResponseUsernameAndEmailList> responseObserver) {
        ResponseUsernameAndEmailList responseUser = userService.getFavouriteUsers(request.getRequestOrResponse());

        responseObserver.onNext(responseUser);
        responseObserver.onCompleted();
    }

    @Override
    public void followRecipe(UserAndFavouriteRecipe request, StreamObserver<ResponseOrRequestString> responseObserver) {
        String responseUser = null;
        try {
            responseUser = recipesService.followRecipe(request.getIdRecipe(), request.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ResponseOrRequestString response = ResponseOrRequestString.newBuilder()
                .setRequestOrResponse(responseUser)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void unfollowRecipe(UserAndFavouriteRecipe request, StreamObserver<ResponseOrRequestString> responseObserver) {
        String responseUser = null;
        try {
            responseUser = recipesService.unfollowRecipe(request.getIdRecipe(), request.getUsername());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ResponseOrRequestString response = ResponseOrRequestString.newBuilder()
                .setRequestOrResponse(responseUser)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void favouriteRecipes(ResponseOrRequestString request, StreamObserver<RecipeResponseBasicList> responseObserver) {
        RecipeResponseBasicList responseUser = recipesService.getFavouriteRecipes(request.getRequestOrResponse());

        responseObserver.onNext(responseUser);
        responseObserver.onCompleted();
    }
}
