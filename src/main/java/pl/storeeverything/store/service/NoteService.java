package pl.storeeverything.store.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.storeeverything.store.model.CategoryDetails;
import pl.storeeverything.store.model.NotesDetails;
import pl.storeeverything.store.model.UserDetails;
import pl.storeeverything.store.repo.NoteRepo;
import pl.storeeverything.store.repo.UserRepo;

import java.util.*;

@Service
public class NoteService {
    @Autowired
    private final NoteRepo noteRepo;
    @Autowired
    private final UserRepo userRepo;

    public NoteService(NoteRepo noteRepo, UserRepo userRepo) {
        this.noteRepo = noteRepo;
        this.userRepo = userRepo;
    }

    public List<NotesDetails> getAllNotes() {
        return noteRepo.findAll();
    }

    public NotesDetails saveNotes(NotesDetails notesDetails){
        return noteRepo.save(notesDetails);
    }
    public List<NotesDetails> findAllByUserIDD(Long id){
        return noteRepo.findALlByUserId(id);
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
//    public List<NotesDetails> sortNotesByCategoryAlphabetically(List<NotesDetails> notes){
//        List<NotesDetails> sortedNotes = new ArrayList<>(notes);
//        sortedNotes.sort(Comparator.comparing(NotesDetails::getTitle, Comparator.reverseOrder()));
//        return sortedNotes;
//    }

    public List<NotesDetails> sortNotesByCategoryAlphabetically(List<NotesDetails> notes) {
        List<NotesDetails> sortedNotes = new ArrayList<>(notes);
        sortedNotes.sort(Comparator.comparing(n -> n.getCategory().getName(), String.CASE_INSENSITIVE_ORDER));
        return sortedNotes;
    }
    public List<NotesDetails> sortNotesByCategoryNonAlphabetically(List<NotesDetails> notes) {
        List<NotesDetails> sortedNotes = new ArrayList<>(notes);
        sortedNotes.sort(Comparator.comparing(n -> n.getCategory().getName(), Comparator.reverseOrder()));
        return sortedNotes;
    }

    public List<NotesDetails> showNotesOfMostPopularCategory(List<NotesDetails> notes) {
        // Count the occurrences of each category
        Map<CategoryDetails, Integer> categoryCount = new HashMap<>();
        for (NotesDetails note : notes) {
            CategoryDetails category = note.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 1);
        }

        // Find the most popular category
        CategoryDetails mostPopularCategory = Collections.max(
                categoryCount.entrySet(),
                Map.Entry.comparingByValue()
        ).getKey();

        // Filter the notes to include only those belonging to the most popular category
        List<NotesDetails> filteredNotes = new ArrayList<>();
        for (NotesDetails note : notes) {
            if (note.getCategory().equals(mostPopularCategory)) {
                filteredNotes.add(note);
            }
        }

        return filteredNotes;
    }

    public List<NotesDetails> findNotesByDateBetween(Date startDate, Date endDate) {
        return noteRepo.findByDateBetween(startDate, endDate);
    }
    public void shareNoteWithUser(Long Id, String username) {
        NotesDetails notesDetails = noteRepo.findAllById(Id);
        UserDetails user = userRepo.findByUsername(username);
            if (user != null) {
                notesDetails.getSharedWith().add(user);
                noteRepo.save(notesDetails);
            }
        }

    public List<NotesDetails> getSharedNotes(String username) {
        UserDetails user = userRepo.findByUsername(username);
        return noteRepo.findBySharedWith(user);
    }
    public NoteRepo getNoteRepo() {
        return noteRepo;
    }
}
