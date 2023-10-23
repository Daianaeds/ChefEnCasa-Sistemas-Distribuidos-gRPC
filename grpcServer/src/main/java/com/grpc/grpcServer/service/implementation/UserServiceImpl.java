package com.grpc.grpcServer.service.implementation;

import com.grpc.grpcServer.IsMod;
import com.grpc.grpcServer.ResponseUsernameAndEmailList;
import com.grpc.grpcServer.UserAuth;
import com.grpc.grpcServer.UserBasic;
import com.grpc.grpcServer.UserRequest;
import com.grpc.grpcServer.entities.Recipe;
import com.grpc.grpcServer.entities.User;
import com.grpc.grpcServer.mapper.UserMapper;
import com.grpc.grpcServer.repositories.UserRepository;
import com.grpc.grpcServer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
        if (exist) throw new Exception("Los datos ingresados ya estan registrados. Intente nuevamente");
        User userSave = userRepository.save(user);
        return userSave.getId();
    }

    @Transactional
    @Override
    public User saveUser(User user) throws Exception {
        return userRepository.save(user);
    }

    @Override
    public ResponseUsernameAndEmailList findAll() throws Exception {
        List<User> users = userRepository.findAll();
        ResponseUsernameAndEmailList userList = userMapper.convertUsertoResponseList(users);
        return userList;
    }

    @Override
    public IsMod auth(UserAuth auth) throws Exception {
        return IsMod.newBuilder()
                .setIsMod(authentication(auth.getUsername(), auth.getPassword()))
                .build();
    }

    @Override
    public User find(UserAuth auth) {
        User User = userRepository.findByUsername(auth.getUsername());
        return User;
    }

    @Transactional
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
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

    private boolean authentication(String username, String password) throws Exception {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new Exception("Los datos ingresados no son correctos. Intente nuevamente");
        if (!user.getPassword().equals(password))
            throw new Exception("Los datos ingresados no son correctos. Intente nuevamente");
        return user.isMod();
    }

    @Override
    public String followUser(String favouriteUsername, String username) {
        User user = userRepository.findByUsername(username);
        User favouriteUser = userRepository.findByUsername(favouriteUsername);
        if (user == null || favouriteUser == null) {
            return "Falló en agregar el usuario " + favouriteUsername;
        }

        user.getFavourites().add(favouriteUser);
        userRepository.save(user);
        return ("Se agregó al usuario " + favouriteUsername);
    }

    @Override
    public String unfollowUser(String favouriteUsername, String username) {
        User user = userRepository.findByUsername(username);
        User favouriteUser = userRepository.findByUsername(favouriteUsername);
        if (user == null || favouriteUser == null) {
            return "Falló en eliminar el usuario " + favouriteUsername;
        }
        user.getFavourites().remove(favouriteUser);
        userRepository.save(user);
        return ("Se eliminó al usuario " + favouriteUsername);
    }

    @Override
    public ResponseUsernameAndEmailList getFavouriteUsers(String username) {
        List<User> favouriteUsers = userRepository.findByFavouriteUser(username);
        return userMapper.convertUsertoResponseList(favouriteUsers);
    }

    @Override
    public List<Recipe> getFavouriteRecipes(String username) {
        return userRepository.findByFavouriteRecipes(username);
    }
}
