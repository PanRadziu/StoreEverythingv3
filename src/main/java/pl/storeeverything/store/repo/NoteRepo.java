package pl.storeeverything.store.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.storeeverything.store.model.NotesDetails;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface NoteRepo extends JpaRepository<NotesDetails,Long> {
    List<NotesDetails> findByDateBetween(Date startDate, Date endDate);
}

