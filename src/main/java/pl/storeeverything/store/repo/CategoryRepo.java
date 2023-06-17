package pl.storeeverything.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.storeeverything.store.model.CategoryDetails;

import java.util.Optional;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryDetails,Long> {
    Optional<CategoryDetails> findByName(String name);
}
