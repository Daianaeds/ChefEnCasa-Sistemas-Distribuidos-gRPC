package com.grpc.grpcServer.entities;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Builder
@ToString
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String name;

    @NotNull
    @Unique
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favourite_user", joinColumns=@JoinColumn(name="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_favourite"))
    private List<User> favourites = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "recipes_author", joinColumns=@JoinColumn(name="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_recipes"))
    private List<Recipe> recipes  = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "favorite_recipes_user", joinColumns=@JoinColumn(name="id_user"),
            inverseJoinColumns=@JoinColumn(name="id_favorite_recipes"))
    private List<Recipe> favoriteRecipes = new ArrayList<>();

    public User() {
    }
}
