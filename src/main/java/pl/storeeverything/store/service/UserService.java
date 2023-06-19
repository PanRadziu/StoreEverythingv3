package pl.storeeverything.store.service;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.storeeverything.store.model.UserDetails;
import pl.storeeverything.store.repo.RoleRepo;
import pl.storeeverything.store.repo.UserRepo;

import java.util.List;

@Service
@Data
@Getter
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

    public UserDetails findUserById(Long Id){
        return userRepo.findUserDetailsById(Id);
    }
    public List<UserDetails> getAllUsers() {
        return userRepo.findAll();
    }
    public UserDetails saveUser(UserDetails userDetails){
        return userRepo.save(userDetails);
    }

    public UserRepo getUserRepo() {
        return userRepo;
    }

}
