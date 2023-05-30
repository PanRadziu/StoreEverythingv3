package pl.storeeverything.store.controler;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.storeeverything.store.model.NotesDetails;
import pl.storeeverything.store.service.NoteService;

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




}

