package pl.storeeverything.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.storeeverything.store.model.UserDetails;

@Repository
public interface UserRepo extends JpaRepository<pl.storeeverything.store.model.UserDetails, Long> {
    UserDetails findByUsername(String username);
}
