package com.grpc.grpcServer.mapper;


import com.google.protobuf.InvalidProtocolBufferException;
import com.grpc.grpcServer.*;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    public User convertUserNewRequestToUser(UserRequest request) {

        return User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .build();

    }

    public UserBasic convertUsertoUserBasic(User user) {

        UserBasic userBasic = UserBasic.newBuilder()
                .setName(user.getName())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .build();
        return userBasic;
    }

    public ResponseUsernameAndEmailList convertUsertoResponseList(List<User> users) {
        ResponseUsernameAndEmailList.Builder responseBuilder = ResponseUsernameAndEmailList.newBuilder();
        for (User userEntity : users) {
            ResponseUsernameAndEmail response = ResponseUsernameAndEmail.newBuilder()
                    .setUsername(userEntity.getUsername())
                    .setEmail(userEntity.getEmail())
                    .build();
            responseBuilder.addResponse(response);
        }
        return responseBuilder.build();
    }

    public UserResponse convertUsertoUserResponse(User user) {

        UserResponse userResponse = UserResponse.newBuilder()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .build();
        return userResponse;
    }

    public UserAuth convertUsertoUserAuth(User user) {

        UserAuth userResponse = UserAuth.newBuilder()
                .setUsername(user.getUsername())
                .build();
        return userResponse;
    }
}
