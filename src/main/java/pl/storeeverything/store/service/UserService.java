package pl.storeeverything.store.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.storeeverything.store.model.UserDetails;
import pl.storeeverything.store.repo.RoleRepo;
import pl.storeeverything.store.repo.UserRepo;

import java.util.Arrays;

@Service
@Data
public class UserService{

    @Autowired
    private final UserRepo userRepo;
    @Autowired
    private final RoleRepo roleRepo;

    public UserService(UserRepo userRepo, RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
    }
    public UserDetails findByUsername(String username){
        return userRepo.findByUsername(username);
    }

    public UserDetails saveUser(UserDetails userDetails){
        return userRepo.save(userDetails);
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }

//    public void addUser(UserDetails userDetails){
//        UserDetails user = new UserDetails();
//        user.setUsername(userDetails.getUsername());
//        user.setPassword(userDetails.getPassword());
//        user.setFirstname(userDetails.getFirstname());
//        user.setLastname(userDetails.getLastname());
//        user.setAge(userDetails.getAge());
//
//        user.setId_roles(Arrays.asList(roleRepo.findRoleDetailsByName("ROLE_LIMITED_USER")));
//
//        userRepo.save(user);
//
//
//
//
//    }

}
