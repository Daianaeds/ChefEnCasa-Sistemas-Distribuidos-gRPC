package com.grpc.grpcServer.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String description;

    @Lob
    private String steps;

    @Column(name = "time_minutes")
    private int timeMinutes;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "ingredients_recipes",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id"))
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "id", fetch = FetchType.EAGER)
    private List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "id")
    private List<Comment> comments = new ArrayList<>();

    private boolean isDelete = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && Objects.equals(title, recipe.title) && Objects.equals(author, recipe.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author);
    }
}
