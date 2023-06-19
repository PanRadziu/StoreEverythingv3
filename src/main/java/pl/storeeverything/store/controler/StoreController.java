package pl.storeeverything.store.controler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public String listNotes(@AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails loggedUser, Model model, HttpServletRequest request, HttpServletResponse response) {
        String isLogged = loggedUser.getUsername();
        UserDetails userDet = userService.findByUsername(isLogged);
        Long id = userDet.getId();

        // Retrieve the sorting option from the cookie
        Cookie[] cookies = request.getCookies();
        String sortingOption = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("sortingOption")) {
                    sortingOption = cookie.getValue();
                    break;
                }
            }
        }

        List<NotesDetails> notesDetailsList;
        if (sortingOption != null && !sortingOption.isEmpty()) {
            // Apply sorting based on the retrieved option
            if (sortingOption.equals("titleAlph")) {
                notesDetailsList = noteService.sortNotesByTitleAlphabetically(noteService.findAllByUserIDD(id));
            } else if (sortingOption.equals("titleNonAlph")) {
                notesDetailsList = noteService.sortNotesByTitleNonAlphabetically(noteService.findAllByUserIDD(id));
            } else if (sortingOption.equals("categoryAlph")) {
                notesDetailsList = noteService.sortNotesByCategoryAlphabetically(noteService.findAllByUserIDD(id));
            } else if (sortingOption.equals("categoryNonAlph")) {
                notesDetailsList = noteService.sortNotesByCategoryNonAlphabetically(noteService.findAllByUserIDD(id));
            } else if (sortingOption.equals("countCategory")) {
                notesDetailsList = noteService.showNotesOfMostPopularCategory(noteService.findAllByUserIDD(id));
            } else {
                notesDetailsList = noteService.findAllByUserIDD(id);
            }
        } else {
            notesDetailsList = noteService.findAllByUserIDD(id);
        }

        model.addAttribute("notes", notesDetailsList);

        // Save the sorting option in a cookie
        Cookie sortingCookie = new Cookie("sortingOption", sortingOption);
        sortingCookie.setMaxAge(30 * 24 * 60 * 60); // Set the cookie expiration time (30 days in this example)
        response.addCookie(sortingCookie);

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
    public String getSortedNotes(@RequestParam("sortingOption") String sortingOption, Model model, HttpServletResponse response) {
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

            // Save the sorting option in a cookie
            Cookie sortingCookie = new Cookie("sortingOption", sortingOption);
            sortingCookie.setMaxAge(30 * 24 * 60 * 60); // Set the cookie expiration time (30 days in this example)
            response.addCookie(sortingCookie);
        } else {
            sortedNotes = allNotes;
        }

        model.addAttribute("notes", sortedNotes);

        return "notes";
    }

    @PostMapping("/notes/filter")
    public String filter(@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                         @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                         Model model) {
        List<NotesDetails> filteredNotes = noteService.findNotesByDateBetween(startDate, endDate);
        model.addAttribute("notes", filteredNotes);
        return "notes";  // replace with your notes list view page
    }

    @GetMapping("/notes/login")
    public String login(@AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails loggedUser,Model model) {
        String isloged = loggedUser.getUsername();
        UserDetails userDet = userService.findByUsername(isloged);
        Long id = userDet.getId();
        List<NotesDetails> notesDetailsList = noteService.findAllByUserIDD(id);
        LocalDate currentDate = LocalDate.now();
        List<NotesDetails> notesToRemind = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (NotesDetails note : notesDetailsList) {
            LocalDate remindDate = LocalDate.parse(note.getRemind_date(), formatter);
            if (remindDate.equals(currentDate)) {
                notesToRemind.add(note);
            }
        }

        model.addAttribute("reminderNotes", notesToRemind);

        if (notesToRemind.isEmpty()) {
            return "redirect:/notes";
        } else {
            return "remind";
        }
    }
}



