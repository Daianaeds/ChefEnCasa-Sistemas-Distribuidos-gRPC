package com.grpc.grpcServer.entities;

import lombok.Builder;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "incompleteRecipes")
public class IncompleteRecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "time")
    private int time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id", table = "incompleteRecipes")
    private User user;

    public IncompleteRecipe() {
    }

    public IncompleteRecipe(Integer id, String title, String description, String category, int time, User user) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.time = time;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
