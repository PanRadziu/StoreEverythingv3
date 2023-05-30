package pl.storeeverything.store.controler;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.storeeverything.store.model.NotesDetails;
import pl.storeeverything.store.service.NoteService;

import java.util.List;

@Controller
public class StoreController {
    private final NoteService noteService;

    public StoreController(NoteService noteService) {
        this.noteService = noteService;
    }


    @GetMapping("/notes")
    public String listNotes(Model model){
        model.addAttribute("notes", noteService.getAllNotes());
        return "notes";
    }

    @GetMapping("/notes/new")
    public String createNotes(Model model) {
        NotesDetails notesDetails = new NotesDetails();
        model.addAttribute("note", notesDetails);
        return "create_notes";
    }

    @PostMapping("/notes")
    public String saveNotes(@ModelAttribute("note") NotesDetails notesDetails){

        noteService.saveNotes(notesDetails);
        return "redirect:/notes";
    }

    @GetMapping("/notes/edit/{id}")
    public String editNote(@PathVariable Long id, Model model){
        model.addAttribute("note", noteService.findNoteById(id));
        return "edit_notes";
    }

    @PostMapping("/notes/{id}")
    public String UpdateNote(@PathVariable Long id ,@ModelAttribute("note") NotesDetails notesDetails, Model model){
        NotesDetails existingNote =noteService.findNoteById(id);
        existingNote.setId(notesDetails.getId());
        existingNote.setTitle(notesDetails.getTitle());
        existingNote.setDescription(notesDetails.getDescription());
        existingNote.setLink(notesDetails.getLink());
        existingNote.setDate(notesDetails.getDate());
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

