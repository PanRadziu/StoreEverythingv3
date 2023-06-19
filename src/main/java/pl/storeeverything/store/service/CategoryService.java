package pl.storeeverything.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.storeeverything.store.model.CategoryDetails;
import pl.storeeverything.store.model.NotesDetails;
import pl.storeeverything.store.repo.CategoryRepo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private final CategoryRepo categoryRepo;

    public CategoryService(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }
    public CategoryDetails findCategoryById(Long id){
        return categoryRepo.findById(id).get();
    }
    public List<CategoryDetails> getAllCategories(){
        return categoryRepo.findAll();
    }

    public CategoryDetails saveCategory(CategoryDetails categoryDetails){
        return categoryRepo.save(categoryDetails);
    }

    public Optional<CategoryDetails> findCategoryName(String name){
        return categoryRepo.findByName(name);
    }

}
