package pl.storeeverything.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.storeeverything.store.model.RoleDetails;
@Repository
public interface RoleRepo extends JpaRepository <RoleDetails, Long> {

    public RoleDetails findRoleDetailsByName(String name);

}
