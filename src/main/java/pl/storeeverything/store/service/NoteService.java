package pl.storeeverything.store.service;

import org.springframework.stereotype.Service;
import pl.storeeverything.store.model.NotesDetails;
import pl.storeeverything.store.repo.NoteRepo;

import java.util.List;
@Service
public class NoteService {
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

}
