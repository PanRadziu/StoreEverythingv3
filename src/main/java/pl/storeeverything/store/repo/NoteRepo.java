package pl.storeeverything.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.storeeverything.store.model.NotesDetails;

public interface NoteRepo extends JpaRepository<NotesDetails,Long> {

}

