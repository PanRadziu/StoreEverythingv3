package pl.storeeverything.store.service;

import org.springframework.stereotype.Service;
import pl.storeeverything.store.model.CategoryDetails;
import pl.storeeverything.store.repo.CategoryRepo;

import java.util.List;
@Service
public class CategoryService {
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public List<CategoryDetails> getAllCategories(){
        return categoryRepo.findAll();
    }

    public CategoryDetails saveCategory(CategoryDetails categoryDetails){
        return categoryRepo.save(categoryDetails);
    }
}
