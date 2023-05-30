package pl.storeeverything.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.storeeverything.store.model.CategoryDetails;


public interface CategoryRepo extends JpaRepository<CategoryDetails,Long> {
}
