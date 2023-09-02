package com.grpc.grpcServer.service;

import com.grpc.grpServer.RecipeRequest;
import com.grpc.grpServer.RecipeResponse;
import com.grpc.grpServer.ServiceGrpc;
import com.grpc.grpServer.UserAuth;
import com.grpc.grpServer.UserListRequest;
import com.grpc.grpServer.UserListResponse;
import com.grpc.grpServer.UserBasic;
import com.grpc.grpServer.UserRequest;
import com.grpc.grpServer.UserResponse;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@GrpcService
public class ServiceImpl extends ServiceGrpc.ServiceImplBase {

    @Autowired
    UserService userService;

    @Autowired
    RecipesService recipesService;

    @Override
    public void newUser(UserRequest request, StreamObserver<UserResponse> responseObserver){

        try {
            int id = userService.save(request);
            UserResponse response = UserResponse.newBuilder()
                    .setId(id)
                    .setUsername(request.getUsername())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }catch(Exception e){
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

        }catch(Exception e){
            UserBasic response = UserBasic.newBuilder()
                    .setError(e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void listUser(UserListRequest request, StreamObserver<UserListResponse> responseObserver) {

        try {
            List<UserBasic> users = userService.findAll(request);

            UserListResponse response = UserListResponse.newBuilder()
                    .addAllUsers(users)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }catch(Exception e){
            UserListResponse response = UserListResponse.newBuilder()
                    .setError(e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
    }

    @Override
    public void newRecipe(RecipeRequest request, StreamObserver<RecipeResponse> responseObserver) {

        try {
            userService.auth(request.getAuth());
            RecipeResponse response = recipesService.newRecipe(request);

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        }catch(Exception e){
            RecipeResponse response = RecipeResponse.newBuilder()
                    .setError(e.getMessage())
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }


    }


}
