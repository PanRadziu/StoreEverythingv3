package pl.storeeverything.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.storeeverything.store.model.CategoryDetails;
import pl.storeeverything.store.model.NotesDetails;
import pl.storeeverything.store.repo.NoteRepo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    @Autowired
    private final NoteRepo noteRepo;

    public NoteService(NoteRepo noteRepo) {
        this.noteRepo = noteRepo;
    }

    public List<NotesDetails> getAllNotes() {
        return noteRepo.findAll();
    }

    public NotesDetails saveNotes(NotesDetails notesDetails){
        return noteRepo.save(notesDetails);
    }

    public NotesDetails findNoteById(Long id){
        return noteRepo.findById(id).get();
    }

    public NotesDetails editNotes(NotesDetails notesDetails){
        return noteRepo.save(notesDetails);
    }

    public void deleteNoteById(Long id){
        noteRepo.deleteById(id);
    }

    //alfabetycznie po tytulach
    public List<NotesDetails> sortNotesByTitleAlphabetically(List<NotesDetails> notes){
        List<NotesDetails> sortedNotes = new ArrayList<>(notes);
        sortedNotes.sort(Comparator.comparing(NotesDetails::getTitle));
        return sortedNotes;
    }
    public List<NotesDetails> sortNotesByTitleNonAlphabetically(List<NotesDetails> notes){
        List<NotesDetails> sortedNotes = new ArrayList<>(notes);
        sortedNotes.sort(Comparator.comparing(NotesDetails::getTitle, Comparator.reverseOrder()));
        return sortedNotes;
    }
    //alfabetycznie po kategoriach to trzeba poprawic bo kategorie od nowa ogarniam XD
//    public List<NotesDetails> sortNotesByCategoryAlphabetically(List<NotesDetails> notes){
//        List<NotesDetails> sortedNotes = new ArrayList<>(notes);
//        sortedNotes.sort(Comparator.comparing(NotesDetails::getCategory));
//        return sortedNotes;
//    }
//    public List<NotesDetails> sortNotesByCategoryNonAlphabetically(List<NotesDetails> notes){
//        List<NotesDetails> sortedNotes = new ArrayList<>(notes);
//        sortedNotes.sort(Comparator.comparing(CategoryDetails::getName, Comparator.reverseOrder()));
//        return sortedNotes;
//    }
    public List<NotesDetails> sortNotesByDate(List<NotesDetails> notes){
        List<NotesDetails> sortedNotes = new ArrayList<>(notes);
        sortedNotes.sort(Comparator.comparing(NotesDetails::getDate));
        return sortedNotes;
    }

}
