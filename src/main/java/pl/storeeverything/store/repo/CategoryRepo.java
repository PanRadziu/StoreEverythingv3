package pl.storeeverything.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.storeeverything.store.model.CategoryDetails;

import java.util.Optional;


public interface CategoryRepo extends JpaRepository<CategoryDetails,Long> {
    Optional<CategoryDetails> findByName(String name);
}
