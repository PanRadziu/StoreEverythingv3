package pl.storeeverything.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


@Entity
@Table(name = "category")
public class CategoryDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private Long id;
    @NotNull
    @Pattern(regexp = "[a-z]{3,20}",message = "Category cannot exceed 20 characters")
    private String name;


    public CategoryDetails(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDetails() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
