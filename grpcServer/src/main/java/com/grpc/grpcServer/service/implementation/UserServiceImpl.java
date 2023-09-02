package com.grpc.grpcServer.service.implementation;

import com.grpc.grpServer.UserAuth;
import com.grpc.grpServer.UserBasic;
import com.grpc.grpServer.UserListRequest;
import com.grpc.grpServer.UserRequest;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.mapper.UserMapper;
import com.grpc.grpcServer.repositories.UserRepository;
import com.grpc.grpcServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public int save(UserRequest request) throws Exception {
       User user = userMapper.convertUserNewRequestToUser(request);
       Boolean exist = userRepository.existsByUsernameOrEmail(request.getUsername(), request.getEmail());
       if(exist) throw new Exception("Los datos ingresados ya estan registrados. Intente nuevamente");
       User userSave = userRepository.save(user);
       return userSave.getId();
    }

    @Override
    public List<UserBasic> findAll(UserListRequest request) throws Exception {
        authentication(request.getUsername(), request.getPassword());
        List<User> users = userRepository.findAll();
        List<UserBasic> usersBasic = users.stream().map(user -> userMapper.convertUsertoUserBasic(user)).collect(Collectors.toList());
        return usersBasic;
    }

    @Override
    public UserBasic auth(UserAuth auth) throws Exception {
        return authentication(auth.getUsername(), auth.getPassword());
    }

    @Override
    public User find(UserAuth auth) {
        User User = userRepository.findByUsername(auth.getUsername());
        return User;
    }

    @Transactional
    @Override
    public void addRecipe(Recipe recipe, UserAuth auth) {
        User user = find(auth);
        List<Recipe> recipeList = user.getRecipes();
        recipeList.add(recipe);
        user.setRecipes(recipeList);
        userRepository.save(user);
    }

    private UserBasic authentication(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username);
        if(user == null) throw new Exception("Los datos ingresados no son correctos. Intente nuevamente");
        if( ! user.getPassword().equals(password))throw new Exception("Los datos ingresados no son correctos. Intente nuevamente");
        return userMapper.convertUsertoUserBasic(user);
    }
}
