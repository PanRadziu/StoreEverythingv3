package pl.storeeverything.store.controler;

import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.storeeverything.store.model.CategoryDetails;
import pl.storeeverything.store.model.NotesDetails;
import pl.storeeverything.store.model.UserDetails;
import pl.storeeverything.store.service.CategoryService;
import pl.storeeverything.store.service.NoteService;
import pl.storeeverything.store.service.UserService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class StoreController {
    private final NoteService noteService;
    private final CategoryService categoryService;
    private final UserService userService;




    public StoreController(NoteService noteService, CategoryService categoryService, UserService userService) {
        this.noteService = noteService;
        this.categoryService = categoryService;
        this.userService = userService;

    }


    @GetMapping("/notes")
    public String listNotes(@AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails loggedUser, Model model){
        String isloged = loggedUser.getUsername();
        UserDetails userDet = userService.findByUsername(isloged);
        Long id = userDet.getId();
        List<NotesDetails> notesDetailsList = noteService.findAllByUserIDD(id);
        model.addAttribute("notes", notesDetailsList);
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
    public String saveNotes(@AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails loggedUser, @Valid @ModelAttribute("note") NotesDetails notesDetails, BindingResult result, Model model){
        String isloged = loggedUser.getUsername();
        UserDetails userDet = userService.findByUsername(isloged);
        if(result.hasErrors()){
            System.out.println("w ifie notatkowym");
            model.addAttribute("categories",categoryService.getAllCategories());
            return "create_notes";
        }
        if(notesDetails.getDate()==null) {
            Date date = new Date();
            notesDetails.setDate(date);
        }
        notesDetails.setUser(userDet);
        noteService.saveNotes(notesDetails);
        return "redirect:/notes";
    }

    @GetMapping("/notes/edit/{id}")
    public String editNote(@PathVariable Long id ,Model model){
        model.addAttribute("note", noteService.findNoteById(id));
        model.addAttribute("categories",categoryService.getAllCategories());
        return "edit_notes";
    }

    @GetMapping("/notes/share/{id}")
    public String shareNote(@PathVariable Long id ,Model model){
        model.addAttribute("note", noteService.findNoteById(id));
        String editUrl = "/notes/edit/" + id;
        model.addAttribute("editUrl", editUrl);
        return "share_notes";
    }

    @GetMapping("/notes/display/{id}")
    public String displayNote(@PathVariable Long id ,Model model){
        model.addAttribute("note", noteService.findNoteById(id));
        model.addAttribute("categories",categoryService.getAllCategories());
        String editUrl = "/notes/edit/" + id;
        model.addAttribute("editUrl", editUrl);
        return "display_notes";
    }

    @PostMapping("/notes/{id}")
    public String UpdateNote(@PathVariable Long id ,@Valid @ModelAttribute("note") NotesDetails notesDetails,BindingResult result, Model model){
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
            } else if (sortingOption.equals("categoryAlph")) {
                sortedNotes = noteService.sortNotesByCategoryAlphabetically(allNotes);
            } else if (sortingOption.equals("categoryNonAlph")) {
                sortedNotes = noteService.sortNotesByCategoryNonAlphabetically(allNotes);
            } else if (sortingOption.equals("countCategory")) {
                sortedNotes = noteService.showNotesOfMostPopularCategory(allNotes);
            } else {
                sortedNotes = allNotes;
            }
        }else {
            sortedNotes = allNotes;
        }

        model.addAttribute("notes", sortedNotes);

        return "notes";
    }

    @PostMapping("/notes/filter")
    public String filterNotes(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                              @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                              Model model) {
        List<NotesDetails> filteredNotes = noteService.getNotesByDateRange(startDate, endDate);

        model.addAttribute("notes", filteredNotes);
        return "notes";
    }

}

