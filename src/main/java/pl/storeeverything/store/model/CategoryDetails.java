package pl.storeeverything.store.model;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class CategoryDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    public CategoryDetails(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDetails() {
    }
}
