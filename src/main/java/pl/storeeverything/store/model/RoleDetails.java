package pl.storeeverything.store.model;

import jakarta.persistence.*;


@Entity
@Table(name = "role")
public class RoleDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;

    @Column(name = "name")
    private String name;

    public RoleDetails(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public RoleDetails() {

    }
    public long MOJE(){
        return id = (long) 1;
    }
    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RoleDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
