package com.grpc.grpcServer.mapper;


import com.google.protobuf.InvalidProtocolBufferException;
import com.grpc.grpcServer.*;
import com.grpc.grpcServer.ResponseUsernameAndEmail;
import com.grpc.grpcServer.ResponseUsernameAndEmailList;
import com.grpc.grpcServer.UserBasic;
import com.grpc.grpcServer.UserRequest;
import com.grpc.grpcServer.entities.PopularUser;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.repositories.PopularUserRepository;
import com.grpc.grpcServer.service.PopularService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
public class UserMapper {


    //hice el metodo aca para evitar un bucle
    @Autowired
    PopularUserRepository popularUserRepository;

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


    private int scoreUserById(int id) throws Exception {
        PopularUser popularUser =  popularUserRepository.findByIdUser(id);
        if(popularUser == null) throw new Exception("El usuario aun no tiene popularidad");
        int score = popularUser.getScore();
        return  score;
    }

    public ResponseUsernameAndEmailList convertUsertoResponseList(List<User> users) {
        ResponseUsernameAndEmailList.Builder responseBuilder = ResponseUsernameAndEmailList.newBuilder();
        int score = 0;
        for (User userEntity : users) {

            try{
                score = scoreUserById(userEntity.getId());
            }catch (Exception e){
                log.error("Error en obtener usuario en UserMapper : ", e.getMessage());
            }

            ResponseUsernameAndEmail response = ResponseUsernameAndEmail.newBuilder()
                    .setUsername(userEntity.getUsername())
                    .setEmail(userEntity.getEmail())
                    .setScore(score)
                    .build();
            responseBuilder.addResponse(response);
            score =0;
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
