package pl.storeeverything.store.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.management.relation.Role;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@AllArgsConstructor
@Table(name = "user")
public class UserDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false,updatable = false)
    private Long id;
    @Column(name = "name")
    @Size(min = 3, max = 20, message = "name has to 3-20 letters long")
    private String firstname;
    @Size(min = 3, max = 50, message = "lastname has to 3-50 letters long")
    @Column(name = "lastname")
    private String lastname;
    @Size(min = 3, max = 20, message = "login has to 3-20 letters long")
    @Column(name = "username")
    private String username;
    @Size(min = 5, message = "password has to minimum 5 letters long")
    @Column(name = "password")
    private String password;
    @DecimalMin(value = "18", message = "must be 18 or older")
    @Column(name = "age")
    private Integer age;

//    @GeneratedValue(strategy = GenerationType.TABLE)
    @ManyToOne
    @JoinColumn(name = "id_roles")
    private RoleDetails id_roles;
    public UserDetails() {
        id_roles = new RoleDetails();
        id_roles.setId(3L);
    }
    public Collection<GrantedAuthority> getRoles() {
        return List.of(new SimpleGrantedAuthority(id_roles.getName()));
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }



    @Override
    public String toString() {
        return "UserDetails{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", id_roles=" + id_roles +
                '}';
    }


    public RoleDetails getId_roles() {
        return id_roles;
    }

    public void setId_roles(RoleDetails id_roles) {
        this.id_roles = id_roles;
    }
}
