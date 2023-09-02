package com.grpc.grpcServer.mapper;



import com.grpc.grpServer.UserAuth;
import com.grpc.grpServer.UserBasic;
import com.grpc.grpServer.UserRequest;
import com.grpc.grpServer.UserResponse;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserMapper {

    public User convertUserNewRequestToUser(UserRequest request){

        return User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();

    }

    public UserBasic convertUsertoUserBasic(User user){

        UserBasic userBasic = UserBasic.newBuilder()
                .setName(user.getName())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .build();
       return userBasic;
    }

    public UserResponse convertUsertoUserResponse(User user){

        UserResponse userResponse = UserResponse.newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .build();
        return userResponse;
    }

    public UserAuth convertUsertoUserAuth(User user){

        UserAuth userResponse = UserAuth.newBuilder()
                .setUsername(user.getUsername())
                .build();
        return userResponse;
    }

}
