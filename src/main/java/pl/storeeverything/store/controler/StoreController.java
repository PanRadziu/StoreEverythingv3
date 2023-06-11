package pl.storeeverything.store.controler;

import jakarta.validation.Valid;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.storeeverything.store.model.CategoryDetails;
import pl.storeeverything.store.model.NotesDetails;
import pl.storeeverything.store.service.CategoryService;
import pl.storeeverything.store.service.NoteService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class StoreController {
    private final NoteService noteService;
    private final CategoryService categoryService;

    public StoreController(NoteService noteService, CategoryService categoryService) {
        this.noteService = noteService;
        this.categoryService = categoryService;
    }


    @GetMapping("/notes")
    public String listNotes(Model model){
        model.addAttribute("notes", noteService.getAllNotes());
        return "notes";
    }

    @GetMapping("/notes/new")
    public String createNotes(Model model) {
        model.addAttribute("note", new NotesDetails());
        model.addAttribute("categories",categoryService.getAllCategories());
        return "create_notes";
    }

    @GetMapping("/notes/category")
    public String getNoteFromCategory (Model model){
        model.addAttribute("category_new", new CategoryDetails());
        return "category";
    }
    @PostMapping("/notes/category")
    public String addCategory(@Valid @ModelAttribute("category_new") CategoryDetails categoryDetails, BindingResult result, Model model){
        Optional<CategoryDetails> categoryExists = categoryService.findCategoryName(categoryDetails.getName());
        if(result.hasErrors() || categoryExists.isPresent()){
            return "category";
        }
        else{
            categoryService.saveCategory(categoryDetails);
            return "redirect:/notes";
        }
    }
    @PostMapping("/notes")
    public String saveNotes(@Valid @ModelAttribute("note") NotesDetails notesDetails, BindingResult result, Model model){
        if(result.hasErrors()){
            System.out.println("w ifie notatkowym");
            model.addAttribute("categories",categoryService.getAllCategories());
            return "create_notes";
        }
        if(notesDetails.getDate()==null) {
            Date date = new Date();
            notesDetails.setDate(date);
        }
        noteService.saveNotes(notesDetails);
        return "redirect:/notes";
    }

    @GetMapping("/notes/edit/{id}")
    public String editNote(@PathVariable Long id ,Model model){
        model.addAttribute("note", noteService.findNoteById(id));
        model.addAttribute("categories",categoryService.getAllCategories());
        return "edit_notes";
    }

    @PostMapping("/notes/{id}")
    public String UpdateNote( @PathVariable Long id ,@Valid @ModelAttribute("note") NotesDetails notesDetails,BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("categories",categoryService.getAllCategories());
            return "edit_notes";
        }
        NotesDetails existingNote = noteService.findNoteById(id);
        existingNote.setId(notesDetails.getId());
        existingNote.setTitle(notesDetails.getTitle());
        existingNote.setDescription(notesDetails.getDescription());
        existingNote.setLink(notesDetails.getLink());
        existingNote.setRemind_date(notesDetails.getRemind_date());
        existingNote.setCategory(notesDetails.getCategory());
        noteService.editNotes(existingNote);
        return "redirect:/notes";
    }

    @GetMapping("/notes/{id}")
    public String deleteNotes(@PathVariable Long id){
        noteService.deleteNoteById(id);
        return "redirect:/notes";
    }

    @GetMapping("/notes/sort")
    public String getSortedNotes(@RequestParam("sortingOption") String sortingOption, Model model) {
        List<NotesDetails> sortedNotes = null;

        List<NotesDetails> allNotes = noteService.getAllNotes();
        if (sortingOption != null && !sortingOption.isEmpty()) {
            if (sortingOption.equals("titleAlph")) {
                sortedNotes = noteService.sortNotesByTitleAlphabetically(allNotes);
            } else if (sortingOption.equals("titleNonAlph")) {
                sortedNotes = noteService.sortNotesByTitleNonAlphabetically(allNotes);
//            } else if (sortingOption.equals("categoryAlph")) {
//                sortedNotes = noteService.sortNotesByCategoryAlphabetically(allNotes);
//            } else if (sortingOption.equals("categoryNonAlph")) {
//                sortedNotes = noteService.sortNotesByCategoryNonAlphabetically(allNotes);
            } else if (sortingOption.equals("date")) {
                sortedNotes = noteService.sortNotesByDate(allNotes);
            } else {
                sortedNotes = allNotes;
            }
        }else {
            sortedNotes = allNotes;
        }

        model.addAttribute("notes", sortedNotes);

        return "notes";
    }

}

